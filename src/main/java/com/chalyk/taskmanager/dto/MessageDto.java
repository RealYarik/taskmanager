package com.chalyk.taskmanager.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class MessageDto {

    private Long id;

    @NotEmpty(message = "Text should not be empty")
    @Size(min = 1, max = 150, message = "Text should be between 1 and 150 characters")
    private String text;

    private String owner;

    private String receiver;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
