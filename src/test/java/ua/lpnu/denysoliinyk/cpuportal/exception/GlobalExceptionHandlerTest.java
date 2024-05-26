package ua.lpnu.denysoliinyk.cpuportal.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import ua.lpnu.denysoliinyk.cpuportal.service.MailService;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class GlobalExceptionHandlerTest {
    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @MockBean
    private MailService mailService;

    @Test
    void exception() {
        Exception ex = new RuntimeException("Test exception");
        MockHttpServletRequest request = new MockHttpServletRequest();

        Map<String, Object> response = globalExceptionHandler.exception(ex, request);

        assertEquals("Test exception", response.get("message"));
        assertEquals(request.getRequestURI(), response.get("path"));

        verify(mailService, times(1)).sendMail("Exception handling", ex.getMessage());
    }
}