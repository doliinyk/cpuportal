package ua.lpnu.denysoliinyk.cpuportal.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.lpnu.denysoliinyk.cpuportal.dto.request.UserRequestDto;
import ua.lpnu.denysoliinyk.cpuportal.entity.User;
import ua.lpnu.denysoliinyk.cpuportal.repository.UserRepository;
import ua.lpnu.denysoliinyk.cpuportal.service.UserService;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void update(User user, UserRequestDto userRequestDto) {
        user = userRepository.save(user);
        if (userRequestDto.username() != null) {
            user.setUsername(userRequestDto.username());
        }
        if (userRequestDto.password() != null) {
            user.setPassword(passwordEncoder.encode(userRequestDto.password()));
        }
        log.info("User {} was updated", user.getUsername());
    }

    @Override
    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
        log.info("User {} was deleted", user.getUsername());
    }
}
