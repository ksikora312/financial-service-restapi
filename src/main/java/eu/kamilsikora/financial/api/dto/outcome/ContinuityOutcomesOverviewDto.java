package eu.kamilsikora.financial.api.dto.outcome;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ContinuityOutcomesOverviewDto {
    private List<ContinuityOutcomeOverviewDto> continuityOutcomes;
}
