package com.chalyk.taskmanager.service;

import com.chalyk.taskmanager.dto.SolutionDto;
import com.chalyk.taskmanager.model.Solution;
import com.chalyk.taskmanager.model.Task;
import com.chalyk.taskmanager.repo.SolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
public class SolutionService {

    private final SolutionRepository solutionRepository;
    private final TaskService taskService;

    @Autowired
    public SolutionService(SolutionRepository solutionRepository, TaskService taskService) {
        this.solutionRepository = solutionRepository;
        this.taskService = taskService;
    }

    public Solution findSolutionById(Long id) {
        return solutionRepository.findSolutionById(id);
    }

    public Integer getSizeSolutionsByTaskId(Long taskId) {
        return solutionRepository.getSizeSolutionsByTaskId(taskId);
    }

    public List<Solution> findSolutionsByTaskId(Long taskId) {
        return solutionRepository.findSolutionsByTaskId(taskId);
    }

    public void save(SolutionDto solutionDto) {
        Solution solution = new Solution();
        Task task = taskService.findTaskById(solutionDto.getTaskId());
        solution.setCode(solutionDto.getCode());
        solution.setTaskAndSolution(task);
        solution.setSolved(false);
        solution.setCreateDate(LocalDateTime.now());

        if (Boolean.FALSE.equals(task.getClosed())) {
            solutionRepository.save(solution);
        }
    }
}
