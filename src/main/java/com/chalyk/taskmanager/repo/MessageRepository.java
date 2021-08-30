package com.chalyk.taskmanager.repo;

import com.chalyk.taskmanager.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MessageRepository extends JpaRepository<Message, Long> {

    Message findMessageByOwnerId(Long ownerId);

    Message findMessageByReceiverId(Long receiverId);

    List<Message> findMessagesByOwnerId(Long ownerId);

    List<Message> findMessagesByReceiverId(Long receiverId);

    List<Message> findMessagesByOwnerIdOrReceiverId(Long ownerId, Long receiverId);
}
