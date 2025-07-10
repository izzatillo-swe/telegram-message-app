package com.company.telegrammessageapp.service;


import com.company.telegrammessageapp.dto.message.MessageResponseDTO;
import com.company.telegrammessageapp.entity.Message;
import com.company.telegrammessageapp.entity.User;
import com.company.telegrammessageapp.exception.RestException;
import com.company.telegrammessageapp.mapper.MessageMapper;
import com.company.telegrammessageapp.repository.MessageRepository;
import com.company.telegrammessageapp.telegram.MyTelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final MyTelegramBot telegramBot;

    @SneakyThrows
    @Transactional
    public MessageResponseDTO sendMessage(String message) {
        User user = UserContextService.getCurrentUserFromContext();

        if (user.getChatId() != null) {
            SendMessage sendMessage = new SendMessage(user.getChatId().toString(),
                    user.getUsername() + ", я получил от тебя сообщение:\n" + message);
            telegramBot.execute(sendMessage);
        } else {
            throw RestException.badRequest("Подключитесь к Telegram-боту, чтобы получать сообщения.\n" +
                    "Бот: @message_telegram_app_bot");
        }

        Message savedMessage = messageRepository.saveAndFlush(new Message(
                message,
                user
        ));

        return messageMapper.toResponse(savedMessage);
    }

    public List<MessageResponseDTO> getUserMessages() {
        User user = UserContextService.getCurrentUserFromContext();

        return messageRepository.findAllByUserId(user.getId()).stream()
                .map(messageMapper::toResponse)
                .toList();
    }

}
