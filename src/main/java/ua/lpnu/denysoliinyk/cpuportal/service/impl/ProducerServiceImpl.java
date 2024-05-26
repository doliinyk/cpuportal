package ua.lpnu.denysoliinyk.cpuportal.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.lpnu.denysoliinyk.cpuportal.dto.request.ProducerRequestDto;
import ua.lpnu.denysoliinyk.cpuportal.dto.response.ProducerResponseDto;
import ua.lpnu.denysoliinyk.cpuportal.entity.Producer;
import ua.lpnu.denysoliinyk.cpuportal.mapper.ProducerMapper;
import ua.lpnu.denysoliinyk.cpuportal.repository.ProducerRepository;
import ua.lpnu.denysoliinyk.cpuportal.service.ProducerService;

import static org.springframework.data.jpa.domain.Specification.where;
import static ua.lpnu.denysoliinyk.cpuportal.repository.ProducerRepository.descriptionContains;
import static ua.lpnu.denysoliinyk.cpuportal.repository.ProducerRepository.nameContains;

@Log4j2
@Service
public class ProducerServiceImpl extends BaseEntityServiceImpl<Producer, ProducerResponseDto, ProducerRequestDto, ProducerRepository, ProducerMapper> implements ProducerService {
    public ProducerServiceImpl(ProducerRepository repository, ProducerMapper mapper) {
        super(repository, mapper);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProducerResponseDto> getAll(Pageable pageable, String filter) {
        log.info("All producers were requested");
        return repository.findAll(where(nameContains(filter)).or(descriptionContains(filter)), pageable)
                .map(mapper::toDto);
    }
}
