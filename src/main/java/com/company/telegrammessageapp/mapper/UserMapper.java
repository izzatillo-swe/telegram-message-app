package com.company.telegrammessageapp.mapper;


import com.company.telegrammessageapp.dto.user.UserResponseDTO;
import com.company.telegrammessageapp.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public UserResponseDTO toResponse(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getCreatedDate()
        );
    }

}