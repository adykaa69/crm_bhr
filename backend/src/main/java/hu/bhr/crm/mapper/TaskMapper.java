package hu.bhr.crm.mapper;

import hu.bhr.crm.controller.dto.TaskResponse;
import hu.bhr.crm.model.Task;
import hu.bhr.crm.repository.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(source = "customer.id", target = "customerId")
    Task taskEntityToTask(TaskEntity taskEntity);

    @Mapping(target = "customer", ignore = true)
    TaskEntity taskToTaskEntity(Task task);

    TaskResponse taskToTaskResponse(Task task);
}
