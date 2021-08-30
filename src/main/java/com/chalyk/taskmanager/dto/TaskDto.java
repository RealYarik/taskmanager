package com.chalyk.taskmanager.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class TaskDto {

    private Long id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 1, max = 255, message = "Name should be between 1 and 255 characters")
    private String name;

    @NotEmpty(message = "Description should not be empty")
    @Size(min = 1, max = 255, message = "Name should be between 1 and 255 characters")
    private String description;

    private String author;

    private String executor;

    private Boolean isClosed;

    private Integer solutionNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }

    public Integer getSolutionNumber() {
        return solutionNumber;
    }

    public void setSolutionNumber(Integer solutionNumber) {
        this.solutionNumber = solutionNumber;
    }

}
