package eu.kamilsikora.financial.api.dto.outcome;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategorySummaryDto {
    private String category;
    private Double moneySpent;
    private List<OutcomeDetailsDto> outcomes;

    CategorySummaryDto(final List<OutcomeDetailsDto> outcomes) {
        this.outcomes = outcomes;
        this.moneySpent = this.outcomes.stream()
                .mapToDouble(OutcomeDetailsDto::getValue)
                .sum();
        this.category = outcomes.get(0).getCategory();
    }

}
