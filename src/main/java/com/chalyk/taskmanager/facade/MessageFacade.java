package com.chalyk.taskmanager.facade;

import com.chalyk.taskmanager.dto.MessageDto;
import com.chalyk.taskmanager.model.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageFacade {

    public MessageDto messageToMessageDto(Message message) {
        MessageDto messageDto = new MessageDto();
        messageDto.setId(message.getId());
        messageDto.setText(message.getText());
        messageDto.setOwner(message.getOwner().getLogin());
        messageDto.setReceiver(message.getReceiver().getLogin());
        messageDto.setSendDate(message.getSendDate());

        return messageDto;
    }

}
