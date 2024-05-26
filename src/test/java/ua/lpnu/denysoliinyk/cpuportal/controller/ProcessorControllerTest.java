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
import ua.lpnu.denysoliinyk.cpuportal.dto.response.ProcessorResponseDto;
import ua.lpnu.denysoliinyk.cpuportal.service.MailService;
import ua.lpnu.denysoliinyk.cpuportal.service.ProcessorService;

import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProcessorController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProcessorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProcessorService service;

    @MockBean
    private MailService mailService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAll() throws Exception {
        Pageable pageable = Pageable.ofSize(10);
        Page<ProcessorResponseDto> page = Page.empty(pageable);

        when(service.getAll(pageable, Map.of())).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/processors")).andExpect(status().isOk());

        verify(service, times(1)).getAll(pageable, Map.of());
    }

    @Test
    void get() throws Exception {
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

        when(service.get(uuid)).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/processors/" + uuid))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDto)));
    }
}