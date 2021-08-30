package com.chalyk.taskmanager.payload.request;

import javax.validation.constraints.NotEmpty;

public class LoginRequest {

    @NotEmpty(message = "Login cannot be empty")
    private String login;
    @NotEmpty(message = "Password cannot be empty")
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
