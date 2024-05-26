package ua.lpnu.denysoliinyk.cpuportal.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.lpnu.denysoliinyk.cpuportal.dto.request.SocketRequestDto;
import ua.lpnu.denysoliinyk.cpuportal.dto.response.SocketResponseDto;
import ua.lpnu.denysoliinyk.cpuportal.entity.Socket;
import ua.lpnu.denysoliinyk.cpuportal.mapper.SocketMapper;
import ua.lpnu.denysoliinyk.cpuportal.repository.SocketRepository;
import ua.lpnu.denysoliinyk.cpuportal.service.SocketService;

import static ua.lpnu.denysoliinyk.cpuportal.repository.SocketRepository.nameContains;

@Log4j2
@Service
public class SocketServiceImpl extends BaseEntityServiceImpl<Socket, SocketResponseDto, SocketRequestDto, SocketRepository, SocketMapper> implements SocketService {
    public SocketServiceImpl(SocketRepository repository, SocketMapper mapper) {
        super(repository, mapper);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SocketResponseDto> getAll(Pageable pageable, String filter) {
        log.info("All sockets were requested");
        return repository.findAll(nameContains(filter), pageable).map(mapper::toDto);
    }
}
