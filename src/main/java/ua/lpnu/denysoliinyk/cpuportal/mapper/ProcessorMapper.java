package ua.lpnu.denysoliinyk.cpuportal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.lpnu.denysoliinyk.cpuportal.dto.request.ProcessorRequestDto;
import ua.lpnu.denysoliinyk.cpuportal.dto.response.ProcessorResponseDto;
import ua.lpnu.denysoliinyk.cpuportal.entity.Processor;
import ua.lpnu.denysoliinyk.cpuportal.entity.Producer;
import ua.lpnu.denysoliinyk.cpuportal.entity.Socket;
import ua.lpnu.denysoliinyk.cpuportal.entity.User;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        uses = {
                ProducerMapper.class,
                SocketMapper.class
        })
public interface ProcessorMapper extends BaseEntityMapper<Processor, ProcessorResponseDto, ProcessorRequestDto> {
    @Mapping(target = "uuid", ignore = true)
    Processor toEntity(ProcessorRequestDto processorRequestDto, Producer producer, Socket socket, User user);
}
