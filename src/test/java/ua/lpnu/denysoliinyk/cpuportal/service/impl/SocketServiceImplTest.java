package ua.lpnu.denysoliinyk.cpuportal.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ua.lpnu.denysoliinyk.cpuportal.dto.request.SocketRequestDto;
import ua.lpnu.denysoliinyk.cpuportal.dto.response.SocketResponseDto;
import ua.lpnu.denysoliinyk.cpuportal.entity.Socket;
import ua.lpnu.denysoliinyk.cpuportal.mapper.SocketMapper;
import ua.lpnu.denysoliinyk.cpuportal.repository.SocketRepository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class SocketServiceImplTest {
    @Autowired
    private SocketServiceImpl socketService;

    @MockBean
    private SocketRepository socketRepository;

    @MockBean
    private SocketMapper socketMapper;

    @Test
    void getAll() {
        when(socketRepository.findAll(any(Specification.class),
                                      any(Pageable.class))).thenReturn(new PageImpl<>(new ArrayList<>()));

        socketService.getAll(PageRequest.ofSize(10), "");

        verify(socketRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    void add() {
        SocketRequestDto requestDto = new SocketRequestDto("");
        Socket socket = new Socket();
        socket.setUuid(UUID.randomUUID());

        when(socketMapper.toEntity(requestDto)).thenReturn(socket);
        when(socketRepository.save(socket)).thenReturn(socket);

        UUID uuid = socketService.add(requestDto);

        assertEquals(socket.getUuid(), uuid);
        verify(socketMapper, times(1)).toEntity(requestDto);
        verify(socketRepository, times(1)).save(socket);
    }

    @Test
    void get() {
        UUID uuid = UUID.randomUUID();
        Socket socket = new Socket();
        SocketResponseDto responseDto = new SocketResponseDto(uuid, "");

        when(socketRepository.findById(uuid)).thenReturn(Optional.of(socket));
        when(socketMapper.toDto(socket)).thenReturn(responseDto);

        SocketResponseDto response = socketService.get(uuid);

        assertEquals(responseDto, response);
        verify(socketRepository, times(1)).findById(uuid);
        verify(socketMapper, times(1)).toDto(socket);
    }

    @Test
    void update() {
        SocketRequestDto requestDto = new SocketRequestDto("");
        Socket socket = new Socket();
        socket.setUuid(UUID.randomUUID());

        when(socketMapper.toEntity(requestDto)).thenReturn(socket);
        when(socketRepository.save(socket)).thenReturn(socket);

        socketService.update(socket.getUuid(), requestDto);

        verify(socketMapper, times(1)).toEntity(requestDto);
        verify(socketRepository, times(1)).save(socket);
    }

    @Test
    void delete() {
        UUID uuid = UUID.randomUUID();
        Socket socket = new Socket();

        when(socketRepository.findById(uuid)).thenReturn(Optional.of(socket));

        socketService.delete(uuid);

        verify(socketRepository, times(1)).delete(socket);
    }
}