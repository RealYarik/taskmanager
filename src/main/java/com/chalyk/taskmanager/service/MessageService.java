package com.chalyk.taskmanager.service;

import com.chalyk.taskmanager.model.Account;
import com.chalyk.taskmanager.model.Message;
import com.chalyk.taskmanager.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void addMessage(Message message) {
        messageRepository.save(message);
    }

    public Map<String, List<Message>> findMessagesForAuthenticatedAccount(Account account) {

        List<Message> messages = messageRepository.findMessagesByOwnerIdOrReceiverId(account.getId(), account.getId());

        return messages.stream()
                .map(message -> message.getOwner().getId().equals(account.getId()) ? message.getReceiver().getLogin() : message.getOwner().getLogin())
                .distinct()
                .collect(Collectors.toMap(
                        communicateAccount -> communicateAccount,
                        communicateAccount -> messages.stream()
                                .filter(message -> message.getOwner().getLogin().equals(communicateAccount) || message.getReceiver().getLogin().equals(communicateAccount))
                                .sorted(Comparator.comparing(Message::getSendDate))
                                .collect(Collectors.toList())
                ));

    }
}
