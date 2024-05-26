package ua.lpnu.denysoliinyk.cpuportal.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProcessorServiceImplTest {
    @Autowired
    private ProcessorServiceImpl processorService;

    @MockBean
    private ProcessorRepository processorRepository;

    @MockBean
    private ProcessorMapper processorMapper;

    @MockBean
    private ProducerRepository producerRepository;

    @MockBean
    private SocketRepository socketRepository;

    @Test
    void getAll() {
        when(processorRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        processorService.getAll(PageRequest.of(0, 10), Map.of("producerIds",
                                                              UUID.randomUUID().toString(),
                                                              "socketIds",
                                                              UUID.randomUUID().toString()));

        verify(processorRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    void add() {
        ProcessorRequestDto requestDto = new ProcessorRequestDto(UUID.randomUUID(),
                                                                 "",
                                                                 UUID.randomUUID(),
                                                                 2,
                                                                 4,
                                                                 1400.0,
                                                                 null,
                                                                 false,
                                                                 400.0);
        User user = new User();
        Producer producer = new Producer();
        Socket socket = new Socket();
        Processor processor = new Processor();
        processor.setUuid(UUID.randomUUID());

        when(producerRepository.findById(any(UUID.class))).thenReturn(Optional.of(producer));
        when(socketRepository.findById(any(UUID.class))).thenReturn(Optional.of(socket));
        when(processorMapper.toEntity(any(ProcessorRequestDto.class),
                                      any(Producer.class),
                                      any(Socket.class),
                                      any(User.class)))
                .thenReturn(processor);
        when(processorRepository.save(processor)).thenReturn(processor);

        UUID uuid = processorService.add(requestDto, user);

        assertEquals(processor.getUuid(), uuid);
        verify(processorRepository, times(1)).save(processor);
    }

    @Test
    void get() {
        UUID uuid = UUID.randomUUID();
        Processor processor = new Processor();
        ProcessorResponseDto responseDto = new ProcessorResponseDto(uuid,
                                                                    null,
                                                                    "model",
                                                                    null,
                                                                    2,
                                                                    4,
                                                                    1400,
                                                                    null,
                                                                    true,
                                                                    400);

        when(processorRepository.findById(uuid)).thenReturn(Optional.of(processor));
        when(processorMapper.toDto(processor)).thenReturn(responseDto);

        ProcessorResponseDto response = processorService.get(uuid);

        assertEquals(responseDto, response);
        verify(processorRepository, times(1)).findById(uuid);
    }

    @Test
    void update() {
        ProcessorRequestDto requestDto = new ProcessorRequestDto(UUID.randomUUID(),
                                                                 "",
                                                                 UUID.randomUUID(),
                                                                 2,
                                                                 4,
                                                                 1400.0,
                                                                 null,
                                                                 false,
                                                                 400.0);
        User user = new User();
        user.setUuid(UUID.randomUUID());
        Producer producer = new Producer();
        Socket socket = new Socket();
        Processor processor = new Processor();
        processor.setUuid(UUID.randomUUID());
        processor.setUser(user);

        when(producerRepository.findById(any(UUID.class))).thenReturn(Optional.of(producer));
        when(socketRepository.findById(any(UUID.class))).thenReturn(Optional.of(socket));
        when(processorMapper.toEntity(any(ProcessorRequestDto.class),
                                      any(Producer.class),
                                      any(Socket.class),
                                      any(User.class)))
                .thenReturn(processor);
        when(processorRepository.save(processor)).thenReturn(processor);

        processorService.update(processor.getUuid(), requestDto, user);

        verify(processorRepository, times(1)).save(processor);
    }

    @Test
    void delete() {
        User user = new User();
        user.setUuid(UUID.randomUUID());
        Processor processor = new Processor();
        processor.setUuid(UUID.randomUUID());
        processor.setUser(user);

        when(processorRepository.findById(processor.getUuid())).thenReturn(Optional.of(processor));

        processorService.delete(processor.getUuid(), user);

        verify(processorRepository, times(1)).delete(processor);
    }


    @Test
    void deleteIfNotOwner() {
        User user = new User();
        User anotherUser = new User();
        user.setUuid(UUID.randomUUID());
        anotherUser.setUuid(UUID.randomUUID());
        Processor processor = new Processor();
        processor.setUuid(UUID.randomUUID());
        processor.setUser(anotherUser);

        when(processorRepository.findById(processor.getUuid())).thenReturn(Optional.of(processor));

        assertThrows(ResponseStatusException.class, () -> processorService.delete(processor.getUuid(), user));
    }

    @Test
    void getAllUserProcessors() {
        User user = new User();
        Pageable pageable = Pageable.unpaged();

        when(processorRepository.findByUser(user, pageable))
                .thenReturn(new PageImpl<>(List.of()));

        processorService.getAllUserProcessors(pageable, user);

        verify(processorRepository, times(1)).findByUser(user, pageable);
    }
}