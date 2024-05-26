package ua.lpnu.denysoliinyk.cpuportal.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.lpnu.denysoliinyk.cpuportal.dto.response.TokenResponseDto;
import ua.lpnu.denysoliinyk.cpuportal.entity.User;
import ua.lpnu.denysoliinyk.cpuportal.repository.UserRepository;
import ua.lpnu.denysoliinyk.cpuportal.service.AuthenticationService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    @Override
    @Transactional
    public UUID register(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user = userRepository.save(user);
        log.info("User {} was registered", user.getUsername());
        return user.getUuid();
    }

    @Override
    @Transactional
    public TokenResponseDto login(User user) {
        user = userRepository.save(user);

        Instant now = Instant.now();
        JwtClaimsSet accessClaims = JwtClaimsSet.builder()
                .issuer("cpuportal")
                .issuedAt(now)
                .expiresAt(now.plus(2, ChronoUnit.HOURS))
                .subject(String.valueOf(user.getUuid()))
                .claim("scope", "")
                .build();
        log.info("User {} signed in", user.getUsername());

        return new TokenResponseDto(jwtEncoder.encode(JwtEncoderParameters.from(accessClaims)).getTokenValue());
    }
}
