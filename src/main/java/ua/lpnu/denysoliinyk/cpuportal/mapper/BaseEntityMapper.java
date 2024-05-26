package ua.lpnu.denysoliinyk.cpuportal.mapper;

import org.mapstruct.Mapping;
import ua.lpnu.denysoliinyk.cpuportal.entity.BaseEntity;

/**
 * @param <T> Entity
 * @param <R> ResponseDto
 * @param <S> RequestDto
 */
public interface BaseEntityMapper<T extends BaseEntity, R, S> {
    @Mapping(target = "uuid", ignore = true)
    T toEntity(S requestDto);

    R toDto(T entity);
}
