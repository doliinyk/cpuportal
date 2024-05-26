package ua.lpnu.denysoliinyk.cpuportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ua.lpnu.denysoliinyk.cpuportal.dto.request.UserRequestDto;
import ua.lpnu.denysoliinyk.cpuportal.dto.response.TokenResponseDto;
import ua.lpnu.denysoliinyk.cpuportal.entity.User;
import ua.lpnu.denysoliinyk.cpuportal.service.AuthenticationService;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UUID register(@RequestBody UserRequestDto userRequestDto) {
        return authenticationService.register(userRequestDto.username(), userRequestDto.password());
    }

    @PostMapping("/login")
    public TokenResponseDto login(@AuthenticationPrincipal User user) {
        return authenticationService.login(user);
    }
}
