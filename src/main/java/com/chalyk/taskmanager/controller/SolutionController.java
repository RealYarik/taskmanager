package com.chalyk.taskmanager.controller;

import com.chalyk.taskmanager.dto.SolutionDto;
import com.chalyk.taskmanager.facade.SolutionFacade;
import com.chalyk.taskmanager.model.Solution;
import com.chalyk.taskmanager.payload.response.MessageResponse;
import com.chalyk.taskmanager.service.SolutionService;
import com.chalyk.taskmanager.util.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@RestController
@CrossOrigin
@RequestMapping("/api/tasks/{taskId}/solutions")
public class SolutionController {

    private final SolutionService solutionService;
    private final SolutionFacade solutionFacade;
    private final ResponseErrorValidation responseErrorValidation;

    @Autowired
    public SolutionController(SolutionService solutionService, SolutionFacade solutionFacade, ResponseErrorValidation responseErrorValidation) {
        this.solutionService = solutionService;
        this.solutionFacade = solutionFacade;
        this.responseErrorValidation = responseErrorValidation;
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

    @PostMapping
    public ResponseEntity<Object> createSolution(@Valid @RequestBody SolutionDto solutionDto, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);

        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }
        solutionService.save(solutionDto);

        return ResponseEntity.ok(new MessageResponse("Solution added successfully"));
    }
}
