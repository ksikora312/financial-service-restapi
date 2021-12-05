package eu.kamilsikora.financial.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckResponseDto {
    private boolean isAvailable;
}
