package com.chalyk.taskmanager.service;

import com.chalyk.taskmanager.dto.MessageDto;
import com.chalyk.taskmanager.model.Account;
import com.chalyk.taskmanager.model.Message;
import com.chalyk.taskmanager.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final AccountService accountService;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountService accountService) {
        this.messageRepository = messageRepository;
        this.accountService = accountService;
    }

    public void addMessage(MessageDto messageDto) {
        Message message = new Message();
        message.setText(messageDto.getText());
        message.setReceiver(accountService.findAccountByLogin(messageDto.getReceiver()));
        message.setOwner(accountService.findAccountByLogin(messageDto.getOwner()));

        if (messageDto.getSendDate() == null) {
            message.setSendDate(LocalDateTime.now());
        }
        message.setSendDate(messageDto.getSendDate());

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
