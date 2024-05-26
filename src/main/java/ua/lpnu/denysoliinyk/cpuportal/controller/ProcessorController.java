package ua.lpnu.denysoliinyk.cpuportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ua.lpnu.denysoliinyk.cpuportal.dto.response.ProcessorResponseDto;
import ua.lpnu.denysoliinyk.cpuportal.service.ProcessorService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/processors")
public class ProcessorController {
    private final ProcessorService processorService;

    @GetMapping
    public Page<ProcessorResponseDto> getAll(@PageableDefault Pageable pageable,
                                             @RequestParam Map<String, Object> filters) {
        return processorService.getAll(pageable, filters);
    }

    @GetMapping("/{uuid}")
    public ProcessorResponseDto get(@PathVariable UUID uuid) {
        return processorService.get(uuid);
    }
}
