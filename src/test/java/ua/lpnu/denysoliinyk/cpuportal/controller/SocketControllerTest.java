package ua.lpnu.denysoliinyk.cpuportal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.lpnu.denysoliinyk.cpuportal.dto.request.SocketRequestDto;
import ua.lpnu.denysoliinyk.cpuportal.dto.response.SocketResponseDto;
import ua.lpnu.denysoliinyk.cpuportal.service.MailService;
import ua.lpnu.denysoliinyk.cpuportal.service.SocketService;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SocketController.class)
@AutoConfigureMockMvc(addFilters = false)
class SocketControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SocketService service;

    @MockBean
    private MailService mailService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAll() throws Exception {
        Pageable pageable = Pageable.ofSize(10);
        Page<SocketResponseDto> page = Page.empty(pageable);

        when(service.getAll(pageable, null)).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/sockets")).andExpect(status().isOk());

        verify(service, times(1)).getAll(pageable, null);
    }

    @Test
    void add() throws Exception {
        UUID uuid = UUID.randomUUID();
        SocketRequestDto requestDto = new SocketRequestDto("name");

        when(service.add(requestDto)).thenReturn(uuid);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/sockets")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("\"" + uuid + "\""));

        verify(service, times(1)).add(requestDto);
    }

    @Test
    void get() throws Exception {
        UUID uuid = UUID.randomUUID();
        SocketResponseDto responseDto = new SocketResponseDto(uuid, "name");

        when(service.get(uuid)).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/sockets/" + uuid))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDto)));

        verify(service, times(1)).get(uuid);
    }

    @Test
    void update() throws Exception {
        UUID uuid = UUID.randomUUID();
        SocketRequestDto requestDto = new SocketRequestDto("name");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/sockets/" + uuid)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        verify(service, times(1)).update(uuid, requestDto);
    }

    @Test
    void delete() throws Exception {
        UUID uuid = UUID.randomUUID();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/sockets/" + uuid))
                .andExpect(status().isOk());

        verify(service, times(1)).delete(uuid);
    }
}