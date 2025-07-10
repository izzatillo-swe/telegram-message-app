package com.company.telegrammessageapp.repository;


import com.company.telegrammessageapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByIdAndDeletedFalse(Long id);

    Optional<User> findByUsernameAndDeletedFalse(String username);

    Optional<User> findByTelegramTokenAndDeletedFalse(String telegramToken);

    boolean existsByUsernameAndDeletedFalse(String username);
    boolean existsByChatIdAndDeletedFalse(Long chatId);

}
