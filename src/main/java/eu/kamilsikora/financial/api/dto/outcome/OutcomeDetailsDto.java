package eu.kamilsikora.financial.api.dto.outcome;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutcomeDetailsDto {
    private Long id;
    private String name;
    private Double value;
    private String outcomeType;
    private String date;
    private String category;
}
