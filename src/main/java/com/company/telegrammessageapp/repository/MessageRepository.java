package com.company.telegrammessageapp.repository;


import com.company.telegrammessageapp.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByUserId(Long userId);

}
