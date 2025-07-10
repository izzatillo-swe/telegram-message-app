package com.company.telegrammessageapp.service;


import com.company.telegrammessageapp.config.security.JWTProvider;
import com.company.telegrammessageapp.dto.token.TokenDTO;
import com.company.telegrammessageapp.dto.user.UserLoginDTO;
import com.company.telegrammessageapp.dto.user.UserRegisterDTO;
import com.company.telegrammessageapp.dto.user.UserResponseDTO;
import com.company.telegrammessageapp.entity.User;
import com.company.telegrammessageapp.exception.RestException;
import com.company.telegrammessageapp.mapper.UserMapper;
import com.company.telegrammessageapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("Auth service")
@RequiredArgsConstructor
@EnableCaching
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTProvider jwtProvider;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserMapper userMapper;

    public UserResponseDTO register(UserRegisterDTO dto) {
        if (userRepository.existsByUsernameAndDeletedFalse(dto.username())) {
            throw RestException.restThrow("Username is already available, please choose another one.", HttpStatus.CONFLICT);
        }

        User user = userRepository.save(new User(
                dto.name(),
                dto.username(),
                passwordEncoder.encode(dto.password())
        ));

        return userMapper.toResponse(user);
    }

    @SneakyThrows
    public TokenDTO login(UserLoginDTO dto) {
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.username(), dto.password())
        );

        User user = (User) authentication.getPrincipal();
        return generateToken(user.getId().toString());
    }

    public TokenDTO refreshToken(String refreshToken) {
        if (!jwtProvider.isRefreshTokenValid(refreshToken)) {
            throw RestException.badRequest("Refresh token is invalid or expired.");
        }

        String userId = jwtProvider.extractUserIdFromRefreshToken(refreshToken);
        if (userId == null) {
            throw RestException.badRequest("Could not extract user info. Please login again.");
        }

        userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> RestException.notFound("User not found"));

        return generateToken(userId);
    }

    private TokenDTO generateToken(String userId) {
        return new TokenDTO(
                jwtProvider.createAccessToken(userId),
                jwtProvider.createRefreshToken(userId),
                "Bearer"
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> RestException.notFound("User not found for username " + username));
    }

}