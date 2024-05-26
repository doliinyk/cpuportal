package ua.lpnu.denysoliinyk.cpuportal.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.lpnu.denysoliinyk.cpuportal.dto.request.SocketRequestDto;
import ua.lpnu.denysoliinyk.cpuportal.dto.response.SocketResponseDto;

public interface SocketService extends BaseEntityService<SocketResponseDto, SocketRequestDto> {
    Page<SocketResponseDto> getAll(Pageable pageable, String filter);
}
