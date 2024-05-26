package ua.lpnu.denysoliinyk.cpuportal.service;

import ua.lpnu.denysoliinyk.cpuportal.dto.request.UserRequestDto;
import ua.lpnu.denysoliinyk.cpuportal.entity.User;

public interface UserService {
    void update(User user, UserRequestDto userRequestDto);

    void delete(User user);
}
