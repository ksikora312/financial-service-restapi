package eu.kamilsikora.financial.api.dto.outcome;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContinuityOutcomeOverviewDto {
    private Long id;
    private String description;
    private Double value;
}
