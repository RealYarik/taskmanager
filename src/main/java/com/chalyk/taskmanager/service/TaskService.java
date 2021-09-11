package com.chalyk.taskmanager.service;

import com.chalyk.taskmanager.dto.TaskDto;
import com.chalyk.taskmanager.facade.TaskFacade;
import com.chalyk.taskmanager.model.Account;
import com.chalyk.taskmanager.model.Role;
import com.chalyk.taskmanager.model.Task;
import com.chalyk.taskmanager.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final AccountService accountService;
    private final TaskFacade taskFacade;

    @Autowired
    public TaskService(TaskRepository taskRepository, AccountService accountService, TaskFacade taskFacade) {
        this.taskRepository = taskRepository;
        this.accountService = accountService;
        this.taskFacade = taskFacade;
    }

    public Task findTaskById(Long id) {
        return taskRepository.findTaskById(id);
    }

    public void createTask(TaskDto taskDto) {
        Task task = taskFacade.taskDtoToTask(taskDto);
        task.setAuthor(accountService.findAccountByLogin(taskDto.getAuthor()));
        task.setExecutor(accountService.findAccountByLogin(taskDto.getExecutor()));
        task.setCreateDate(LocalDateTime.now());
        task.setClosed(false);

        taskRepository.save(task);
    }

    public List<Task> findAllByAuthor(Account account) {
        return taskRepository.findAllByAuthor(account);
    }

    public List<Task> findAllByAuthorId(Long id) {
        return taskRepository.findTasksByAuthorId(id);
    }

    public List<Task> findTasksForAuthenticatedAccount(Account account) {
        List<Task> messages;

        if (account.getRoles().contains(Role.ADMIN) || account.getRoles().contains(Role.MENTOR)) {
            messages = taskRepository.findTaskByAuthorIdOrExecutorId(account.getId(), account.getId());
        } else {
            messages = taskRepository.findTaskByExecutorId(account.getId());
        }

        return messages;
    }
}
