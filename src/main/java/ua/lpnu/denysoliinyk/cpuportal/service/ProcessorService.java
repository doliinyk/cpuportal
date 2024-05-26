package ua.lpnu.denysoliinyk.cpuportal.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.lpnu.denysoliinyk.cpuportal.dto.request.ProcessorRequestDto;
import ua.lpnu.denysoliinyk.cpuportal.dto.response.ProcessorResponseDto;
import ua.lpnu.denysoliinyk.cpuportal.entity.User;

import java.util.Map;
import java.util.UUID;

public interface ProcessorService {
    Page<ProcessorResponseDto> getAll(Pageable pageable, Map<String, Object> filters);

    UUID add(ProcessorRequestDto processorRequestDto, User user);

    ProcessorResponseDto get(UUID uuid);

    void update(UUID uuid, ProcessorRequestDto processorRequestDto, User user);

    void delete(UUID uuid, User user);

    Page<ProcessorResponseDto> getAllUserProcessors(Pageable pageable, User user);
}
