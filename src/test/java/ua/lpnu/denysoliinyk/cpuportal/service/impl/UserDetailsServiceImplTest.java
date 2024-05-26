package ua.lpnu.denysoliinyk.cpuportal.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.lpnu.denysoliinyk.cpuportal.entity.User;
import ua.lpnu.denysoliinyk.cpuportal.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserDetailsServiceImplTest {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void loadUserByUuid() {
        UUID uuid = UUID.randomUUID();
        User user = new User();
        user.setUuid(uuid);
        user.setUsername("testUser");

        when(userRepository.findByUuidOrUsername(uuid, uuid.toString())).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername(uuid.toString());

        assertNotNull(userDetails);
        assertEquals(user.getUsername(), userDetails.getUsername());
        verify(userRepository, times(1)).findByUuidOrUsername(uuid, uuid.toString());
    }

    @Test
    void loadUserByUsername() {
        User user = new User();
        user.setUsername("testUser");

        when(userRepository.findByUuidOrUsername(null, user.getUsername())).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

        assertNotNull(userDetails);
        assertEquals(user.getUsername(), userDetails.getUsername());
        verify(userRepository, times(1)).findByUuidOrUsername(null, user.getUsername());
    }

    @Test
    void loadUserByUsernameNotFound() {
        when(userRepository.findByUuidOrUsername(any(), any())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(""));
    }
}