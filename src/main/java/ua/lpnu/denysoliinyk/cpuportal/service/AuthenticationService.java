package ua.lpnu.denysoliinyk.cpuportal.service;

import ua.lpnu.denysoliinyk.cpuportal.entity.User;
import ua.lpnu.denysoliinyk.cpuportal.dto.response.TokenResponseDto;

import java.util.UUID;

public interface AuthenticationService {
    UUID register(String username, String password);

    TokenResponseDto login(User user);
}
