package com.chalyk.taskmanager.payload.response;

public class InvalidLoginResponse {

    private String message;

    public InvalidLoginResponse() {
        this.message = "Invalid login or password";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
