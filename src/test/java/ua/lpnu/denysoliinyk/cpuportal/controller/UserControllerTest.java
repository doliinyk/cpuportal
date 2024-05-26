package ua.lpnu.denysoliinyk.cpuportal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.lpnu.denysoliinyk.cpuportal.dto.request.ProcessorRequestDto;
import ua.lpnu.denysoliinyk.cpuportal.dto.request.UserRequestDto;
import ua.lpnu.denysoliinyk.cpuportal.dto.response.ProcessorResponseDto;
import ua.lpnu.denysoliinyk.cpuportal.entity.User;
import ua.lpnu.denysoliinyk.cpuportal.service.MailService;
import ua.lpnu.denysoliinyk.cpuportal.service.ProcessorService;
import ua.lpnu.denysoliinyk.cpuportal.service.UserService;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private ProcessorService processorService;

    @MockBean
    private MailService mailService;

    @Autowired
    ObjectMapper objectMapper;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUuid(UUID.randomUUID());
        user.setUsername("testuser");

        Jwt jwt = mock(Jwt.class);
        when(jwt.getSubject()).thenReturn("testuser");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(jwt);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(user);
    }

    @Test
    public void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(user.getUuid().toString()))
                .andExpect(jsonPath("$.username").value(user.getUsername()));
    }

    @Test
    public void update() throws Exception {
        UserRequestDto userRequestDto = new UserRequestDto("newusername", "newpassword");

        mockMvc.perform(put("/api/user").contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userRequestDto)))
                .andExpect(status().isOk());

        verify(userService, times(1)).update(eq(user), any(UserRequestDto.class));
    }

    @Test
    public void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user"))
                .andExpect(status().isOk());

        verify(userService, times(1)).delete(user);
    }

    @Test
    public void testGetAllProcessors() throws Exception {
        UUID uuid = UUID.randomUUID();
        ProcessorResponseDto responseDto = new ProcessorResponseDto(uuid,
                                                                    null,
                                                                    "model",
                                                                    null,
                                                                    2,
                                                                    4,
                                                                    1400,
                                                                    null,
                                                                    true,
                                                                    400);

        when(processorService.getAllUserProcessors(any(PageRequest.class), eq(user)))
                .thenReturn(new PageImpl<>(List.of(responseDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/processors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].uuid").value(uuid.toString()));
    }

    @Test
    public void addProcessor() throws Exception {
        UUID uuid = UUID.randomUUID();
        ProcessorRequestDto processorRequestDto = new ProcessorRequestDto(UUID.randomUUID(),
                                                                          "",
                                                                          UUID.randomUUID(),
                                                                          2,
                                                                          4,
                                                                          1400.0,
                                                                          null,
                                                                          false,
                                                                          400.0);

        when(processorService.add(any(ProcessorRequestDto.class), eq(user))).thenReturn(uuid);

        mockMvc.perform(post("/api/user/processors").contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(processorRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("\"" + uuid + "\""));
    }

    @Test
    public void updateProcessor() throws Exception {
        UUID uuid = UUID.randomUUID();
        ProcessorRequestDto processorRequestDto = new ProcessorRequestDto(UUID.randomUUID(),
                                                                          "",
                                                                          UUID.randomUUID(),
                                                                          2,
                                                                          4,
                                                                          1400.0,
                                                                          null,
                                                                          false,
                                                                          400.0);

        mockMvc.perform(put("/api/user/processors/{uuid}", uuid).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(processorRequestDto)))
                .andExpect(status().isOk());

        verify(processorService, times(1)).update(eq(uuid), any(ProcessorRequestDto.class), eq(user));
    }

    @Test
    public void deleteProcessor() throws Exception {
        UUID processorUuid = UUID.randomUUID();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/processors/{uuid}", processorUuid))
                .andExpect(status().isOk());

        verify(processorService, times(1)).delete(processorUuid, user);
    }
}