package eu.kamilsikora.financial.api.dto.outcome;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewContinuityOutcomeDto {
    private Double value;
    private Integer categoryId;
    private Integer timeIntervalInDays;
}
