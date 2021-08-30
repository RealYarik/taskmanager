package com.chalyk.taskmanager.controller;

import com.chalyk.taskmanager.dto.SolutionDto;
import com.chalyk.taskmanager.facade.SolutionFacade;
import com.chalyk.taskmanager.model.Solution;
import com.chalyk.taskmanager.service.AccountService;
import com.chalyk.taskmanager.service.SolutionService;
import com.chalyk.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@RestController
@CrossOrigin
@RequestMapping("/api/tasks/{taskId}/solutions")
public class SolutionController {

    private final SolutionService solutionService;
    private final TaskService taskService;
    private final SolutionFacade solutionFacade;
    private final AccountService accountService;

    @Autowired
    public SolutionController(SolutionService solutionService, TaskService taskService, SolutionFacade solutionFacade, AccountService accountService) {
        this.solutionService = solutionService;
        this.taskService = taskService;
        this.solutionFacade = solutionFacade;
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<SolutionDto>> getSolutionsBySpecificTaskId(@PathVariable("taskId") Long taskId) {
        List<Solution> solutions = solutionService.findSolutionsByTaskId(taskId);
        List<SolutionDto> solutionDtoList = solutions.stream()
                .map(solutionFacade::solutionToSolutionDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(solutionDtoList, HttpStatus.OK);
    }

    @GetMapping("map")
    public ResponseEntity<Map<Long, Boolean>> getBooleanMapOfSolutions(@PathVariable("taskId") Long taskId) {
        List<Solution> solutions = solutionService.findSolutionsByTaskId(taskId);
        Map<Long, Boolean> booleanMap = solutions.stream()
                .collect(toMap(Solution::getId, Solution::getSolved));

        return new ResponseEntity<>(booleanMap, HttpStatus.OK);
    }
}
