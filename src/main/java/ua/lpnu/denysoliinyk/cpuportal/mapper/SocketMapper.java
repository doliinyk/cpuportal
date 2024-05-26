package ua.lpnu.denysoliinyk.cpuportal.mapper;

import org.mapstruct.Mapper;
import ua.lpnu.denysoliinyk.cpuportal.dto.request.SocketRequestDto;
import ua.lpnu.denysoliinyk.cpuportal.dto.response.SocketResponseDto;
import ua.lpnu.denysoliinyk.cpuportal.entity.Socket;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface SocketMapper extends BaseEntityMapper<Socket, SocketResponseDto, SocketRequestDto> {
}
