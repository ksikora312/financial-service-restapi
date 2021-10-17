package eu.kamilsikora.financial.api.dto.outcome;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutcomeOverviewDto {
    private Long id;
    private String name;
    private Double value;
}
