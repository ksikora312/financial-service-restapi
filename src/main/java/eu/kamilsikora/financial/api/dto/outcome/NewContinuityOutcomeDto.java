package eu.kamilsikora.financial.api.dto.outcome;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewContinuityOutcomeDto {
    private Double value;
    private String description;
    private Long categoryId;
    private Integer timeIntervalInDays;
    private Boolean createOutcome;
}
