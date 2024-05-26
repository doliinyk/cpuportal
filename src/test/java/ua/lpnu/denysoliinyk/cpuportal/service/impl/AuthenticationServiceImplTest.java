package ua.lpnu.denysoliinyk.cpuportal.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import ua.lpnu.denysoliinyk.cpuportal.dto.response.TokenResponseDto;
import ua.lpnu.denysoliinyk.cpuportal.entity.User;
import ua.lpnu.denysoliinyk.cpuportal.repository.UserRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class AuthenticationServiceImplTest {
    @Autowired
    private AuthenticationServiceImpl authenticationService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void register() {
        User user = new User();
        user.setUuid(UUID.randomUUID());
        String username = "username";
        String password = "password";

        when(passwordEncoder.encode(password)).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UUID uuid = authenticationService.register(username, password);

        assertEquals(user.getUuid(), uuid);
        verify(passwordEncoder, times(1)).encode(password);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void login() {
        User user = new User();
        UUID uuid = UUID.randomUUID();
        user.setUuid(uuid);
        user.setUsername("username");
        user.setPassword("encodedPassword");

        when(userRepository.save(user)).thenReturn(user);

        TokenResponseDto tokenResponseDto = authenticationService.login(user);

        assertNotNull(tokenResponseDto);

        verify(userRepository, times(1)).save(user);
    }
}