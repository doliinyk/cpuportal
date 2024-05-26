package ua.lpnu.denysoliinyk.cpuportal.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;
import ua.lpnu.denysoliinyk.cpuportal.entity.BaseEntity;
import ua.lpnu.denysoliinyk.cpuportal.mapper.BaseEntityMapper;
import ua.lpnu.denysoliinyk.cpuportal.repository.BaseEntityRepository;
import ua.lpnu.denysoliinyk.cpuportal.service.BaseEntityService;

import java.util.UUID;

/**
 * @param <T> Entity
 * @param <R> ResponseDto
 * @param <S> RequestDto
 * @param <Q> Repository
 * @param <X> Mapper
 */
@Log4j2
@RequiredArgsConstructor
public abstract class BaseEntityServiceImpl<T extends BaseEntity, R, S, Q extends BaseEntityRepository<T>, X extends BaseEntityMapper<T, R, S>> implements BaseEntityService<R, S> {
    protected final Q repository;
    protected final X mapper;

    @Override
    @Transactional
    public UUID add(S requestDto) {
        T entity = repository.save(mapper.toEntity(requestDto));
        log.info("{} {} was added", entity.getClass().getSimpleName(), entity.getUuid());
        return entity.getUuid();
    }

    @Override
    @Transactional(readOnly = true)
    public R get(UUID uuid) {
        T entity = repository.findById(uuid).orElseThrow();
        log.info("{} {} was requested", entity.getClass().getSimpleName(), entity.getUuid());
        return mapper.toDto(entity);
    }

    @Override
    @Transactional
    public void update(UUID uuid, S requestDto) {
        T entity = mapper.toEntity(requestDto);
        entity.setUuid(uuid);
        repository.save(entity);
        log.info("{} {} was updated", entity.getClass().getSimpleName(), entity.getUuid());
    }

    @Transactional
    @Override
    public void delete(UUID uuid) {
        T entity = repository.findById(uuid).orElseThrow();
        repository.delete(entity);
        log.info("{} {} was deleted", entity.getClass().getSimpleName(), entity.getUuid());
    }
}
