package hu.bhr.crm.service;

import hu.bhr.crm.exception.CustomerNotFoundException;
import hu.bhr.crm.mapper.TaskMapper;
import hu.bhr.crm.model.Task;
import hu.bhr.crm.model.TaskStatus;
import hu.bhr.crm.repository.CustomerRepository;
import hu.bhr.crm.repository.TaskRepository;
import hu.bhr.crm.repository.entity.CustomerEntity;
import hu.bhr.crm.repository.entity.TaskEntity;
import hu.bhr.crm.validation.FieldValidation;
import org.springframework.stereotype.Service;

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

        TaskEntity taskEntity = taskMapper.taskToTaskEntity(task);
        if (task.status() == null) {
            taskEntity.setStatus(TaskStatus.OPEN);
        } else {
            taskEntity.setStatus(task.status());
        }

        if (task.customerId() != null) {
            CustomerEntity customer = customerRepository.findById(task.customerId())
                    .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
            taskEntity.setCustomer(customer);
        }

        TaskEntity savedTaskEntity = taskRepository.save(taskEntity);
        savedTaskEntity = taskRepository.findById(savedTaskEntity.getId())
                .orElseThrow(() -> new RuntimeException("Failed to retrieve saved task"));

        return taskMapper.taskEntityToTask(savedTaskEntity);
    }
}

