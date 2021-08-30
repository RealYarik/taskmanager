package com.chalyk.taskmanager.service;

import com.chalyk.taskmanager.model.Account;
import com.chalyk.taskmanager.model.Role;
import com.chalyk.taskmanager.model.Task;
import com.chalyk.taskmanager.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task findTaskById(Long id) {
        return taskRepository.findTaskById(id);
    }

    public void createTask(Task task) {
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
