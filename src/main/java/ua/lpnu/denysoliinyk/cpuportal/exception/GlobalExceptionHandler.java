package ua.lpnu.denysoliinyk.cpuportal.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.lpnu.denysoliinyk.cpuportal.service.MailService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MailService mailService;

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String, Object> exception(Exception ex, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();

        log.error("Exception was received: {}", ex.getMessage());
        response.put("message", ex.getMessage());
        response.put("timestamp", new Date().toString());
        response.put("path", request.getRequestURI());
        mailService.sendMail("Exception handling", ex.getMessage());

        return response;
    }
}
