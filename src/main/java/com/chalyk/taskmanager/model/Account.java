package com.chalyk.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "accounts")
public class Account implements UserDetails {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @NotEmpty(message = "First name cannot be empty")
    @Size(min = 1, max = 50, message = "Name can be between 1 and 50 characters")
    private String firstName;


    @Column(name = "last_name")
    @NotEmpty(message = "Last name cannot be empty")
    @Size(min = 1, max = 50, message = "Name can be between 1 and 50 characters")
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotEmpty(message = "Login cannot be empty")
    private String login;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "account_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Column(name = "active")
    private boolean isActive;

    @Column(name = "create_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "owner")
    private List<Message> messageOwners = new ArrayList<>();

    @OneToMany(mappedBy = "receiver")
    private List<Message> messageReceivers = new ArrayList<>();

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private List<Task> taskAuthors = new ArrayList<>();

    @OneToMany(mappedBy = "executor")
    private List<Task> taskExecutors = new ArrayList<>();

    public Account() {
    }

    public Account(String firstName, String lastName, Gender gender, String login, String password, LocalDateTime createDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.login = login;
        this.password = password;
        this.createDate = createDate;
    }

    public void addMessageOwner(Message messageOwner) {
        messageOwners.add(messageOwner);
    }

    public void addMessageReceiver(Message messageReceiver) {
        messageReceivers.add(messageReceiver);
    }

    public void addTaskAuthor(Task taskAuthor) {
        taskAuthors.add(taskAuthor);
    }

    public void addTaskExecutor(Task taskExecutor) {
        taskExecutors.add(taskExecutor);
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }


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

    public List<Message> getMessageOwners() {
        return messageOwners;
    }

    public List<Message> getMessageReceivers() {
        return messageReceivers;
    }

    public List<Task> getTaskAuthors() {
        return taskAuthors;
    }

    public List<Task> getTaskExecutors() {
        return taskExecutors;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id.equals(account.id) && login.equals(account.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login);
    }
}
