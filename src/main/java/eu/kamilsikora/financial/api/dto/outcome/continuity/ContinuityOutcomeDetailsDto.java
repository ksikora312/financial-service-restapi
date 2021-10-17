package eu.kamilsikora.financial.api.dto.outcome.continuity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ContinuityOutcomeDetailsDto {
    private Long id;
    private String name;
    private Boolean active;
    private String addedDate;
    private String lastUsage;
    private String nextUsage;
    private Double value;
    private List<CreatedOutcomesDto> producedOutcomes;
}
