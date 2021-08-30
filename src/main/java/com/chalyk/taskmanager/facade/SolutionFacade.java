package com.chalyk.taskmanager.facade;

import com.chalyk.taskmanager.dto.SolutionDto;
import com.chalyk.taskmanager.model.Solution;
import org.springframework.stereotype.Component;

@Component
public class SolutionFacade {


    public SolutionDto solutionToSolutionDto(Solution solution) {
        SolutionDto solutionDto = new SolutionDto();
        solutionDto.setId(solution.getId());
        solutionDto.setCode(solution.getCode());
        solutionDto.setSolved(solution.getSolved());
        solutionDto.setTaskId(solution.getTaskAndSolution().getId());

        return solutionDto;
    }
}
