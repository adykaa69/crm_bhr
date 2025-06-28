package hu.bhr.crm.mapper;

import hu.bhr.crm.controller.dto.TaskRequest;
import hu.bhr.crm.model.Task;

import java.util.UUID;

public class TaskFactory {

    /**
     * Builds a Task from a TaskRequest.
     *
     * @param taskRequest the data transfer object containing the new task details
     * @return one built task in a {@link Task}
     */
    public static Task createTask(TaskRequest taskRequest) {
        return Task.builder()
                .id(UUID.randomUUID())
                .customerId(taskRequest.customerId())
                .title(taskRequest.title())
                .description(taskRequest.description())
                .reminder(taskRequest.reminder())
                .dueDate(taskRequest.dueDate())
                .status(taskRequest.status())
                .build();
    }
}
