package ua.lpnu.denysoliinyk.cpuportal.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.lpnu.denysoliinyk.cpuportal.dto.request.ProducerRequestDto;
import ua.lpnu.denysoliinyk.cpuportal.dto.response.ProducerResponseDto;

public interface ProducerService extends BaseEntityService<ProducerResponseDto, ProducerRequestDto> {
    Page<ProducerResponseDto> getAll(Pageable pageable, String filter);
}
