package com.chalyk.taskmanager.dto;

import javax.validation.constraints.NotEmpty;

public class SolutionDto {

    private Long id;

    @NotEmpty(message = "Code should not be empty")
    private String code;

    private Boolean isSolved;

    private Long taskId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getSolved() {
        return isSolved;
    }

    public void setSolved(Boolean solved) {
        isSolved = solved;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
