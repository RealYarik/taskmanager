package com.chalyk.taskmanager.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 1, max = 255, message = "Name should be between 1 and 255 characters")
    private String name;

    @NotEmpty(message = "Description should not be empty")
    @Size(min = 1, max = 255, message = "Name should be between 1 and 255 characters")
    private String description;

    @Column(name = "create_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime createDate;

    @Column(name = "close_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime closeDate;

    @OneToMany(mappedBy = "taskAndSolution")
    private List<Solution> solutions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "account_id")
    private Account author;

    @ManyToOne
    @JoinColumn(name = "executor_id", referencedColumnName = "account_id")
    private Account executor;

    @Column(name = "is_closed")
    private Boolean isClosed;

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

    public List<Solution> getSolutions() {
        return solutions;
    }

    public void addSolution(Solution solution) {
        solutions.add(solution);
    }

    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }

    public Account getExecutor() {
        return executor;
    }

    public void setExecutor(Account executor) {
        this.executor = executor;
    }

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDateTime closeDate) {
        this.closeDate = closeDate;
    }
}
