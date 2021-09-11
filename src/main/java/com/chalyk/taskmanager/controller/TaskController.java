package com.chalyk.taskmanager.controller;

import com.chalyk.taskmanager.dto.TaskDto;
import com.chalyk.taskmanager.facade.SolutionFacade;
import com.chalyk.taskmanager.facade.TaskFacade;
import com.chalyk.taskmanager.model.Account;
import com.chalyk.taskmanager.model.Task;
import com.chalyk.taskmanager.payload.response.MessageResponse;
import com.chalyk.taskmanager.service.AccountService;
import com.chalyk.taskmanager.service.SolutionService;
import com.chalyk.taskmanager.service.TaskService;
import com.chalyk.taskmanager.util.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    private final ResponseErrorValidation responseErrorValidation;

    @Autowired
    public TaskController(TaskService taskService, SolutionService solutionService, ResponseErrorValidation responseErrorValidation, TaskFacade taskFacade, SolutionFacade solutionFacade, AccountService accountService, ResponseErrorValidation responseErrorValidation1) {
        this.taskService = taskService;
        this.solutionService = solutionService;
        this.taskFacade = taskFacade;
        this.accountService = accountService;
        this.responseErrorValidation = responseErrorValidation1;
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
    public ResponseEntity<Object> getTaskByIdForCurrentAccount(Principal principal, @PathVariable Long id) {
        Task task = taskService.findTaskById(id);
        if (task.getAuthor().getLogin().equals(principal.getName()) ||
                task.getExecutor().getLogin().equals(principal.getName())) {
            TaskDto taskDto = taskFacade.taskToTaskDto(task);
            taskDto.setSolutionNumber(solutionService.getSizeSolutionsByTaskId(task.getId()));

            return new ResponseEntity<>(taskDto, HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MENTOR')")
    public ResponseEntity<Object> createTask(Principal principal, @Valid @RequestBody TaskDto taskDto, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);

        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }
        taskDto.setAuthor(principal.getName());
        taskService.createTask(taskDto);

        return ResponseEntity.ok(new MessageResponse("Task added successfully"));
    }
}
