package com.company.telegrammessageapp.service;


import com.company.telegrammessageapp.entity.User;
import com.company.telegrammessageapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public String generateTelegramToken() {
        User user = UserContextService.getCurrentUserFromContext();

        String token = UUID.randomUUID().toString();
        user.setTelegramToken(token);
        userRepository.save(user);

        return token;
    }

    public boolean registerChatIdForUser(String token, Long chatId) {
        return userRepository.findByTelegramTokenAndDeletedFalse(token)
                .map(user -> {
                    user.setChatId(chatId);
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }

    public boolean existsByChatId(Long chatId) {
        return userRepository.existsByChatIdAndDeletedFalse(chatId);
    }

}