package com.chalyk.taskmanager.dto;

import com.chalyk.taskmanager.model.Gender;
import com.chalyk.taskmanager.model.Role;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

public class AccountDto {

    private Long id;

    @NotEmpty(message = "First name cannot be empty")
    @Size(min = 1, max = 50, message = "Name can be between 1 and 50 characters")
    private String firstName;


    @NotEmpty(message = "Last name cannot be empty")
    @Size(min = 1, max = 50, message = "Name can be between 1 and 50 characters")
    private String lastName;

    private Gender gender;

    @NotEmpty(message = "Login cannot be empty")
    private String login;

    private Set<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
