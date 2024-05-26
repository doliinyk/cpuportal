package ua.lpnu.denysoliinyk.cpuportal.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ua.lpnu.denysoliinyk.cpuportal.dto.request.ProducerRequestDto;
import ua.lpnu.denysoliinyk.cpuportal.dto.response.ProducerResponseDto;
import ua.lpnu.denysoliinyk.cpuportal.entity.Producer;
import ua.lpnu.denysoliinyk.cpuportal.mapper.ProducerMapper;
import ua.lpnu.denysoliinyk.cpuportal.repository.ProducerRepository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProducerServiceImplTest {
    @Autowired
    private ProducerServiceImpl producerService;

    @MockBean
    private ProducerRepository producerRepository;

    @MockBean
    private ProducerMapper producerMapper;

    @Test
    void getAll() {
        when(producerRepository.findAll(any(Specification.class),
                                        any(Pageable.class))).thenReturn(new PageImpl<>(new ArrayList<>()));

        producerService.getAll(PageRequest.ofSize(10), "");

        verify(producerRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    void add() {
        ProducerRequestDto requestDto = new ProducerRequestDto("", null);
        Producer producer = new Producer();
        producer.setUuid(UUID.randomUUID());

        when(producerMapper.toEntity(requestDto)).thenReturn(producer);
        when(producerRepository.save(producer)).thenReturn(producer);

        UUID uuid = producerService.add(requestDto);

        assertEquals(producer.getUuid(), uuid);
        verify(producerMapper, times(1)).toEntity(requestDto);
        verify(producerRepository, times(1)).save(producer);
    }

    @Test
    void get() {
        UUID uuid = UUID.randomUUID();
        Producer producer = new Producer();
        ProducerResponseDto responseDto = new ProducerResponseDto(uuid, "", null);

        when(producerRepository.findById(uuid)).thenReturn(Optional.of(producer));
        when(producerMapper.toDto(producer)).thenReturn(responseDto);

        ProducerResponseDto response = producerService.get(uuid);

        assertEquals(responseDto, response);
        verify(producerRepository, times(1)).findById(uuid);
        verify(producerMapper, times(1)).toDto(producer);
    }

    @Test
    void update() {
        ProducerRequestDto requestDto = new ProducerRequestDto("", null);
        Producer producer = new Producer();
        producer.setUuid(UUID.randomUUID());

        when(producerMapper.toEntity(requestDto)).thenReturn(producer);
        when(producerRepository.save(producer)).thenReturn(producer);

        producerService.update(producer.getUuid(), requestDto);

        verify(producerMapper, times(1)).toEntity(requestDto);
        verify(producerRepository, times(1)).save(producer);
    }

    @Test
    void delete() {
        UUID uuid = UUID.randomUUID();
        Producer producer = new Producer();

        when(producerRepository.findById(uuid)).thenReturn(Optional.of(producer));

        producerService.delete(uuid);

        verify(producerRepository, times(1)).delete(producer);
    }
}