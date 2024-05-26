package ua.lpnu.denysoliinyk.cpuportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.lpnu.denysoliinyk.cpuportal.service.BaseEntityService;

import java.util.Map;
import java.util.UUID;

/**
 * @param <T> ResponseDto
 * @param <R> RequestDto
 * @param <S> Service
 */
@RestController
@RequiredArgsConstructor
public abstract class BaseEntityController<T, R, S extends BaseEntityService<T, R>> {
    protected final S service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID add(@RequestBody R requestDto) {
        return service.add(requestDto);
    }

    @GetMapping("/{uuid}")
    public T get(@PathVariable UUID uuid) {
        return service.get(uuid);
    }

    @PutMapping("/{uuid}")
    public void update(@PathVariable UUID uuid, @RequestBody R requestDto) {
        service.update(uuid, requestDto);
    }

    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid) {
        service.delete(uuid);
    }
}
