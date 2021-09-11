package com.chalyk.taskmanager.controller;

import com.chalyk.taskmanager.dto.MessageDto;
import com.chalyk.taskmanager.facade.MessageFacade;
import com.chalyk.taskmanager.model.Account;
import com.chalyk.taskmanager.model.Message;
import com.chalyk.taskmanager.service.AccountService;
import com.chalyk.taskmanager.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;


@RestController
@CrossOrigin
@RequestMapping("/api/messages")
public class MessageController {

    private final AccountService accountService;
    private final MessageService messageService;
    private final MessageFacade messageFacade;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public MessageController(AccountService accountService, MessageService messageService, MessageFacade messageFacade, SimpMessagingTemplate messagingTemplate) {
        this.accountService = accountService;
        this.messageService = messageService;
        this.messageFacade = messageFacade;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<MessageDto>>> getMessagesForCurrentAccountWithSpecificPenfriend(Principal principal) {
        Account account = accountService.getCurrentAccount(principal);
        Map<String, List<Message>> messageMap = messageService.findMessagesForAuthenticatedAccount(account);
        Map<String, List<MessageDto>> penfriendLoginMessagesMap = messageMap.entrySet().stream()
                .collect(toMap(Map.Entry::getKey, m -> m.getValue().stream()
                        .map(messageFacade::messageToMessageDto)
                        .collect(Collectors.toList())
                ));

        return new ResponseEntity<>(penfriendLoginMessagesMap, HttpStatus.OK);
    }

    @MessageMapping("/send/message/{to}")
    public void sendMessage(@DestinationVariable String to, MessageDto message) {
        messageService.addMessage(message);
        messagingTemplate.convertAndSend("/queue/reply/" + to, message);
    }
}
