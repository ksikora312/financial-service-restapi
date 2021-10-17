package eu.kamilsikora.financial.api.dto.outcome.continuity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateContinuityOutcomeDto {
    private Long id;
    private Double value;
    private String description;
    private Long categoryId;
    private Integer timeIntervalInDays;
}
