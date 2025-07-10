package com.company.telegrammessageapp.mapper;

import com.company.telegrammessageapp.dto.message.MessageResponseDTO;
import com.company.telegrammessageapp.entity.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    public MessageResponseDTO toResponse(Message message) {
        return new MessageResponseDTO(
                message.getId(),
                message.getBody(),
                message.getSentAt()
        );
    }

}