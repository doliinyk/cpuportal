package ua.lpnu.denysoliinyk.cpuportal.mapper;

import org.mapstruct.Mapper;
import ua.lpnu.denysoliinyk.cpuportal.dto.request.ProducerRequestDto;
import ua.lpnu.denysoliinyk.cpuportal.dto.response.ProducerResponseDto;
import ua.lpnu.denysoliinyk.cpuportal.entity.Producer;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ProducerMapper extends BaseEntityMapper<Producer, ProducerResponseDto, ProducerRequestDto> {
}
