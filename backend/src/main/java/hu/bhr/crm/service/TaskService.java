package hu.bhr.crm.service;

import hu.bhr.crm.domain.TaskStatus;
import hu.bhr.crm.exception.CustomerNotFoundException;
import hu.bhr.crm.mapper.TaskMapper;
import hu.bhr.crm.model.Task;
import hu.bhr.crm.repository.CustomerRepository;
import hu.bhr.crm.repository.TaskRepository;
import hu.bhr.crm.repository.entity.CustomerEntity;
import hu.bhr.crm.repository.entity.TaskEntity;
import hu.bhr.crm.validation.FieldValidation;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final CustomerRepository customerRepository;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper, CustomerRepository customerRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.customerRepository = customerRepository;
    }

    /**
     * Saves a new task for a customer.
     * Validates the task's title and checks if the customer exists.
     *
     * @param task the built task to be saved
     * @return the saved {@link Task} object
     * @throws CustomerNotFoundException if the customer with the given ID does not exist (returns HTTP 404 Not Found)
     * @throws hu.bhr.crm.exception.MissingFieldException if title is missing
     */
    public Task saveTask(Task task) {
        FieldValidation.validateNotEmpty(task.title(), "Title");
        FieldValidation.validateNotEmpty(task.status(), "Status");

        TaskEntity taskEntity = taskMapper.taskToTaskEntity(task);

        // Update completedAt timestamp if the task status is completed
        if (task.status() == TaskStatus.completed) {
            taskEntity.setCompletedAt(Timestamp.from(Instant.now()));
        } else {
            taskEntity.setCompletedAt(null);
        }

        // If the task has a customerId, fetch the customer and set it in the task entity
        if (task.customerId() != null) {
            CustomerEntity customer = customerRepository.findById(task.customerId())
                    .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
            taskEntity.setCustomer(customer);
        }

        TaskEntity savedTaskEntity = taskRepository.save(taskEntity);

        return taskMapper.taskEntityToTask(savedTaskEntity);
    }
}

