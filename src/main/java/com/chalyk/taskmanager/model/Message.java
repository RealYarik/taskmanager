package com.chalyk.taskmanager.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @Column(name = "message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Text should not be empty")
    @Size(min = 1, max = 150, message = "Text should be between 1 and 150 characters")
    private String text;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "account_id")
    private Account owner;

    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "account_id")
    private Account receiver;

    @Column(name = "send_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime sendDate;

    @Column(name = "read_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime readDate;

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

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }

    public Account getReceiver() {
        return receiver;
    }

    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }

    public LocalDateTime getSendDate() {
        return sendDate;
    }

    public void setSendDate(LocalDateTime sendDate) {
        this.sendDate = sendDate;
    }

    public LocalDateTime getReadDate() {
        return readDate;
    }

    public void setReadDate(LocalDateTime readDate) {
        this.readDate = readDate;
    }
}
