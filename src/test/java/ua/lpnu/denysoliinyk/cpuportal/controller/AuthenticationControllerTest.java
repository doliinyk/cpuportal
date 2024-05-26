package ua.lpnu.denysoliinyk.cpuportal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import ua.lpnu.denysoliinyk.cpuportal.dto.request.UserRequestDto;
import ua.lpnu.denysoliinyk.cpuportal.dto.response.TokenResponseDto;
import ua.lpnu.denysoliinyk.cpuportal.entity.User;
import ua.lpnu.denysoliinyk.cpuportal.service.AuthenticationService;
import ua.lpnu.denysoliinyk.cpuportal.service.MailService;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService service;

    @MockBean
    private MailService mailService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void register() throws Exception {
        UserRequestDto requestDto = new UserRequestDto("username", "password");
        UUID uuid = UUID.randomUUID();

        when(service.register(requestDto.username(), requestDto.password())).thenReturn(uuid);

        mockMvc.perform(post("/api/auth/register").contentType("application/json")
                                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("\"" + uuid + "\""));

        verify(service, times(1)).register(requestDto.username(), requestDto.password());
    }

    @Test
    void login() throws Exception {
        User user = new User();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null);

        TokenResponseDto token = new TokenResponseDto("token");

        when(service.login(any())).thenReturn(token);

        mockMvc.perform(post("/api/auth/login")
                                .principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token.token()));

        verify(service, times(1)).login(any());
    }
}