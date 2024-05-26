package ua.lpnu.denysoliinyk.cpuportal.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.lpnu.denysoliinyk.cpuportal.dto.request.SocketRequestDto;
import ua.lpnu.denysoliinyk.cpuportal.dto.response.SocketResponseDto;
import ua.lpnu.denysoliinyk.cpuportal.service.SocketService;

import java.util.Map;

@RestController
@RequestMapping("/api/sockets")
public class SocketController extends BaseEntityController<SocketResponseDto, SocketRequestDto, SocketService> {
    public SocketController(SocketService service) {
        super(service);
    }

    @GetMapping
    public Page<SocketResponseDto> getAll(@PageableDefault Pageable pageable,
                                          @RequestParam Map<String, Object> filters) {
        return service.getAll(pageable, (String) filters.get("filter"));
    }
}
