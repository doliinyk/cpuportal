package ua.lpnu.denysoliinyk.cpuportal.service;

import java.util.UUID;

/**
 * @param <T> ResponseDto
 * @param <R> RequestDto
 */
public interface BaseEntityService<T, R> {
    UUID add(R requestDto);

    T get(UUID uuid);

    void update(UUID uuid, R requestDto);

    void delete(UUID uuid);
}
