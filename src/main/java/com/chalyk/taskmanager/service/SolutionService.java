package com.chalyk.taskmanager.service;

import com.chalyk.taskmanager.model.Solution;
import com.chalyk.taskmanager.repo.SolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class SolutionService {

    private final SolutionRepository solutionRepository;

    @Autowired
    public SolutionService(SolutionRepository solutionRepository) {
        this.solutionRepository = solutionRepository;
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

    public void save(Solution solution) {
        solutionRepository.save(solution);
    }
}
