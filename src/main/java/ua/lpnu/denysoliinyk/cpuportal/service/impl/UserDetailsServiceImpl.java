package ua.lpnu.denysoliinyk.cpuportal.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.lpnu.denysoliinyk.cpuportal.repository.UserRepository;

import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String uuidOrUsername) throws UsernameNotFoundException {
        UUID uuid = null;
        try {
            uuid = UUID.fromString(uuidOrUsername);
        } catch (Exception ignored) {
        }
        log.info("User {} was loaded to authorize", uuidOrUsername);
        return userRepository.findByUuidOrUsername(uuid, uuidOrUsername)
                .orElseThrow(() -> new UsernameNotFoundException(uuidOrUsername + " not found"));
    }
}
