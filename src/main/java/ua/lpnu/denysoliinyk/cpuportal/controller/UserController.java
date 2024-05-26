package ua.lpnu.denysoliinyk.cpuportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ua.lpnu.denysoliinyk.cpuportal.dto.request.ProcessorRequestDto;
import ua.lpnu.denysoliinyk.cpuportal.dto.request.UserRequestDto;
import ua.lpnu.denysoliinyk.cpuportal.dto.response.ProcessorResponseDto;
import ua.lpnu.denysoliinyk.cpuportal.dto.response.UserResponseDto;
import ua.lpnu.denysoliinyk.cpuportal.entity.User;
import ua.lpnu.denysoliinyk.cpuportal.service.ProcessorService;
import ua.lpnu.denysoliinyk.cpuportal.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final ProcessorService processorService;

    @GetMapping
    public UserResponseDto get(@AuthenticationPrincipal Jwt jwt) {
        User user = getUser(jwt);
        return new UserResponseDto(user.getUuid(), user.getUsername());
    }

    @PutMapping
    public void update(@AuthenticationPrincipal Jwt jwt, @RequestBody UserRequestDto userRequestDto) {
        userService.update(getUser(jwt), userRequestDto);
    }

    @DeleteMapping
    public void delete(@AuthenticationPrincipal Jwt jwt) {
        userService.delete(getUser(jwt));
    }

    @GetMapping("/processors")
    public Page<ProcessorResponseDto> getAllProcessors(@PageableDefault Pageable pageable,
                                                       @AuthenticationPrincipal Jwt jwt) {
        return processorService.getAllUserProcessors(pageable, getUser(jwt));
    }

    @PostMapping("/processors")
    @ResponseStatus(HttpStatus.CREATED)
    public UUID addProcessor(@RequestBody ProcessorRequestDto processorRequestDto, @AuthenticationPrincipal Jwt jwt) {
        return processorService.add(processorRequestDto, getUser(jwt));
    }

    @PutMapping("/processors/{uuid}")
    public void updateProcessor(@PathVariable UUID uuid,
                                @RequestBody ProcessorRequestDto processorRequestDto,
                                @AuthenticationPrincipal Jwt jwt) {
        processorService.update(uuid, processorRequestDto, getUser(jwt));
    }

    @DeleteMapping("/processors/{uuid}")
    public void deleteProcessor(@PathVariable UUID uuid, @AuthenticationPrincipal Jwt jwt) {
        processorService.delete(uuid, getUser(jwt));
    }

    private User getUser(Jwt jwt) {
        return (User) userDetailsService.loadUserByUsername(jwt.getSubject());
    }
}
