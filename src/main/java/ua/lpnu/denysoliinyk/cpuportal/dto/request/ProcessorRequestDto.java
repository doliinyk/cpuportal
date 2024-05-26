package ua.lpnu.denysoliinyk.cpuportal.dto.request;

import java.util.UUID;

public record ProcessorRequestDto(UUID producerId, String model, UUID socketId, Integer cores, Integer threads,
                                  Double coreClock, Double boostClock, Boolean graphics, Double price) {
}
