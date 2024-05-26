package ua.lpnu.denysoliinyk.cpuportal.dto.response;

import java.util.UUID;

public record ProcessorResponseDto(UUID uuid, ProducerResponseDto producer, String model,
                                   SocketResponseDto socket, int cores, int threads, double coreClock,
                                   Double boostClock, boolean graphics, double price) {
}
