package ua.lpnu.denysoliinyk.cpuportal.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.lpnu.denysoliinyk.cpuportal.dto.request.ProducerRequestDto;
import ua.lpnu.denysoliinyk.cpuportal.dto.response.ProducerResponseDto;
import ua.lpnu.denysoliinyk.cpuportal.service.ProducerService;

import java.util.Map;

@RestController
@RequestMapping("/api/producers")
public class ProducerController extends BaseEntityController<ProducerResponseDto, ProducerRequestDto, ProducerService> {
    public ProducerController(ProducerService service) {
        super(service);
    }

    @GetMapping
    public Page<ProducerResponseDto> getAll(@PageableDefault Pageable pageable,
                                            @RequestParam Map<String, Object> filters) {
        return service.getAll(pageable, (String) filters.get("filter"));
    }
}
