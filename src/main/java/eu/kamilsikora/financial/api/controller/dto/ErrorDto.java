package eu.kamilsikora.financial.api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@Getter
public class ErrorDto {
    private final LocalDateTime timestamp;
    private final int status;
    private final Set<String> errorMessages;
}
