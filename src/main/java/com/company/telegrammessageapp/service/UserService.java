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

    public void registerChatIdForUser(String token, Long chatId) {
        User user = userRepository.findByTelegramTokenAndDeletedFalse(token)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        user.setChatId(chatId);
        userRepository.save(user);
    }

}