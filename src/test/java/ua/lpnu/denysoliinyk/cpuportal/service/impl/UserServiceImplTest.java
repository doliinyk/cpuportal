package ua.lpnu.denysoliinyk.cpuportal.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.lpnu.denysoliinyk.cpuportal.dto.request.UserRequestDto;
import ua.lpnu.denysoliinyk.cpuportal.entity.User;
import ua.lpnu.denysoliinyk.cpuportal.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserServiceImpl userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void update() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        UserRequestDto requestDto = new UserRequestDto("newUsername", "newPassword");

        when(userRepository.save(user)).thenReturn(user);
        when(passwordEncoder.encode(requestDto.password())).thenReturn("encoded");

        userService.update(user, requestDto);
        assertEquals(requestDto.username(), user.getUsername());
        assertEquals("encoded", user.getPassword());
        verify(userRepository, times(1)).save(user);
        verify(passwordEncoder, times(1)).encode(requestDto.password());
    }

    @Test
    void delete() {
        User user = new User();

        userService.delete(user);

        verify(userRepository, times(1)).delete(user);
    }
}