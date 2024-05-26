package ua.lpnu.denysoliinyk.cpuportal.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ua.lpnu.denysoliinyk.cpuportal.dto.request.ProcessorRequestDto;
import ua.lpnu.denysoliinyk.cpuportal.dto.response.ProcessorResponseDto;
import ua.lpnu.denysoliinyk.cpuportal.entity.Processor;
import ua.lpnu.denysoliinyk.cpuportal.entity.Producer;
import ua.lpnu.denysoliinyk.cpuportal.entity.Socket;
import ua.lpnu.denysoliinyk.cpuportal.entity.User;
import ua.lpnu.denysoliinyk.cpuportal.mapper.ProcessorMapper;
import ua.lpnu.denysoliinyk.cpuportal.repository.ProcessorRepository;
import ua.lpnu.denysoliinyk.cpuportal.repository.ProducerRepository;
import ua.lpnu.denysoliinyk.cpuportal.repository.SocketRepository;
import ua.lpnu.denysoliinyk.cpuportal.service.ProcessorService;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.Specification.where;
import static ua.lpnu.denysoliinyk.cpuportal.repository.ProcessorRepository.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProcessorServiceImpl implements ProcessorService {
    private final ProcessorRepository processorRepository;
    private final ProcessorMapper processorMapper;
    private final ProducerRepository producerRepository;
    private final SocketRepository socketRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<ProcessorResponseDto> getAll(Pageable pageable, Map<String, Object> filters) {
        Specification<Processor> specification = where(null);

        Set<UUID> producerIds = new HashSet<>();
        Set<UUID> socketIds = new HashSet<>();

        if (filters.get("producerIds") != null) {
            producerIds = Arrays.stream(((String) filters.get("producerIds")).split(",")).map(String::trim)
                    .map(UUID::fromString).collect(Collectors.toSet());
        }
        if (filters.get("socketIds") != null) {
            socketIds = Arrays.stream(((String) filters.get("socketIds")).split(",")).map(String::trim)
                    .map(UUID::fromString).collect(Collectors.toSet());
        }

        specification = specification.and(hasProducer(producerIds))
                .and(hasModel((String) filters.get("model")))
                .and(hasSocket(socketIds))
                .and(hasCores(getInteger(filters, "minCores"), getInteger(filters, "maxCores")))
                .and(hasThreads(getInteger(filters, "minThreads"), getInteger(filters, "maxThreads")))
                .and(hasCoreClock(getDouble(filters, "minCoreClock"), getDouble(filters, "maxCoreClock")))
                .and(hasBoostClock(getDouble(filters, "minBoostClock"), getDouble(filters, "maxBoostClock")))
                .and(hasGraphics(getBoolean(filters, "graphics")))
                .and(hasPrice(getDouble(filters, "minPrice"), getDouble(filters, "maxPrice")));

        log.info("All processors were requested");
        return processorRepository.findAll(specification, pageable).map(processorMapper::toDto);
    }

    @Override
    @Transactional
    public UUID add(ProcessorRequestDto requestDto, User user) {
        Producer producer = producerRepository.findById(requestDto.producerId()).orElseThrow();
        Socket socket = socketRepository.findById(requestDto.socketId()).orElseThrow();
        Processor processor = processorRepository.save(processorMapper.toEntity(requestDto, producer, socket, user));
        log.info("Processor {} was added", processor.getUuid());
        return processor.getUuid();
    }

    @Override
    @Transactional(readOnly = true)
    public ProcessorResponseDto get(UUID uuid) {
        Processor processor = processorRepository.findById(uuid).orElseThrow();
        log.info("Processor {} was requested", uuid);
        return processorMapper.toDto(processor);
    }

    @Override
    @Transactional
    public void update(UUID uuid, ProcessorRequestDto requestDto, User user) {
        Producer producer = producerRepository.findById(requestDto.producerId()).orElseThrow();
        Socket socket = socketRepository.findById(requestDto.socketId()).orElseThrow();
        Processor processor = processorMapper.toEntity(requestDto, producer, socket, user);

        checkProcessorOwner(processor, user);

        processor.setUuid(uuid);
        processorRepository.save(processor);
        log.info("Processor {} was updated", uuid);
    }

    @Override
    @Transactional
    public void delete(UUID uuid, User user) {
        Processor processor = processorRepository.findById(uuid).orElseThrow();
        checkProcessorOwner(processor, user);
        processorRepository.delete(processor);
        log.info("Processor {} was deleted", processor.getUuid());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProcessorResponseDto> getAllUserProcessors(Pageable pageable, User user) {
        log.info("All user {} processors were requested", user.getUuid());
        return processorRepository.findByUser(user, pageable).map(processorMapper::toDto);
    }

    private void checkProcessorOwner(Processor processor, User user) {
        if (!processor.getUser().getUuid().equals(user.getUuid())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Processor doesn't belong to this user");
        }
    }

    private Integer getInteger(Map<String, Object> filters, String key) {
        Object value = filters.get(key);
        return value instanceof String ? Integer.parseInt((String) value) : null;
    }

    private Double getDouble(Map<String, Object> filters, String key) {
        Object value = filters.get(key);
        return value instanceof String ? Double.parseDouble((String) value) : null;
    }

    private Boolean getBoolean(Map<String, Object> filters, String key) {
        Object value = filters.get(key);
        return value instanceof Boolean ? (Boolean) value : null;
    }
}
