package hu.bhr.crm.service;

import hu.bhr.crm.exception.CustomerNotFoundException;
import hu.bhr.crm.exception.TaskNotFoundException;
import hu.bhr.crm.mapper.TaskMapper;
import hu.bhr.crm.model.Task;
import hu.bhr.crm.model.TaskStatus;
import hu.bhr.crm.repository.TaskRepository;
import hu.bhr.crm.repository.entity.CustomerEntity;
import hu.bhr.crm.repository.entity.TaskEntity;
import hu.bhr.crm.validation.FieldValidation;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final CustomerService customerService;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper, CustomerService customerService) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.customerService = customerService;
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

        setCustomerByIdIfExists(taskEntity, task.customerId());

        TaskEntity savedTaskEntity = taskRepository.save(taskEntity);
        setCompletedAtIfCompleted(savedTaskEntity);

        savedTaskEntity = taskRepository.findById(savedTaskEntity.getId())
                .orElseThrow(() -> new TaskNotFoundException("Failed to retrieve saved task"));
        return taskMapper.taskEntityToTask(savedTaskEntity);
    }

    private void setCustomerByIdIfExists(TaskEntity taskEntity, UUID customerId) {
        if (customerId != null) {
            CustomerEntity customer = customerService.getCustomerEntityById(customerId);
            taskEntity.setCustomer(customer);
        }
    }

    private void setCompletedAtIfCompleted(TaskEntity taskEntity) {
        if (taskEntity.getStatus() == TaskStatus.COMPLETED) {
            taskRepository.setCompletedAtIfCompleted(taskEntity.getId(), TaskStatus.COMPLETED);
        }
    }
}

