package com.chalyk.taskmanager.repo;

import com.chalyk.taskmanager.model.Account;
import com.chalyk.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findTasksByAuthorId(Long id);

    List<Task> findAllByAuthor(Account account);

    List<Task> findTaskByExecutorId(Long id);

    List<Task> findTaskByAuthorIdOrExecutorId(Long authorId, Long executorId);

    Task findTaskById(Long id);
}
