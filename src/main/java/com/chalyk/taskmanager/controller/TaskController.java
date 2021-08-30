package com.chalyk.taskmanager.controller;

import com.chalyk.taskmanager.dto.TaskDto;
import com.chalyk.taskmanager.facade.SolutionFacade;
import com.chalyk.taskmanager.facade.TaskFacade;
import com.chalyk.taskmanager.model.Account;
import com.chalyk.taskmanager.model.Task;
import com.chalyk.taskmanager.service.AccountService;
import com.chalyk.taskmanager.service.SolutionService;
import com.chalyk.taskmanager.service.TaskService;
import com.chalyk.taskmanager.util.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin
public class TaskController {

    private final TaskService taskService;
    private final SolutionService solutionService;
    private final TaskFacade taskFacade;
    private final AccountService accountService;

    @Autowired
    public TaskController(TaskService taskService, SolutionService solutionService, ResponseErrorValidation responseErrorValidation, TaskFacade taskFacade, SolutionFacade solutionFacade, AccountService accountService) {
        this.taskService = taskService;
        this.solutionService = solutionService;
        this.taskFacade = taskFacade;
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getTasksForCurrentAccount(Principal principal) {
        Account account = accountService.getCurrentAccount(principal);
        List<Task> tasks = taskService.findTasksForAuthenticatedAccount(account);
        List<TaskDto> taskDtoList = tasks.stream()
                .map(taskFacade::taskToTaskDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(taskDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskBySpecificAccountId(@PathVariable Long id) {
        Task task = taskService.findTaskById(id);

        TaskDto taskDto = taskFacade.taskToTaskDto(task);
        taskDto.setSolutionNumber(solutionService.getSizeSolutionsByTaskId(task.getId()));

        return new ResponseEntity<>(taskDto, HttpStatus.OK);
    }
}
