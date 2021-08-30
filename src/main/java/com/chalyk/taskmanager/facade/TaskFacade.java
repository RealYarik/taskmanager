package com.chalyk.taskmanager.facade;

import com.chalyk.taskmanager.dto.TaskDto;
import com.chalyk.taskmanager.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskFacade {

    public TaskDto taskToTaskDto(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setName(task.getName());
        taskDto.setDescription(task.getDescription());
        taskDto.setAuthor(task.getAuthor().getLogin());
        taskDto.setExecutor(task.getExecutor().getLogin());
        taskDto.setClosed(task.getClosed());

        return taskDto;
    }
}
