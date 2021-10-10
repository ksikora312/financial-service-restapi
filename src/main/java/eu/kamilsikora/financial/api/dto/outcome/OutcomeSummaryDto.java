package eu.kamilsikora.financial.api.dto.outcome;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class OutcomeSummaryDto {
    private Double moneySpent;
    private List<CategorySummaryDto> outcomesByCategories;

    public OutcomeSummaryDto(final List<OutcomeDetailsDto> allOutcomes) {
        calculateAndSetMoneySpent(allOutcomes);
        Map<String, List<OutcomeDetailsDto>> outcomesByCategory = groupByCategory(allOutcomes);
        createSummariesForEachCategory(outcomesByCategory);
    }

    private void calculateAndSetMoneySpent(final List<OutcomeDetailsDto> allOutcomes) {
        this.moneySpent = allOutcomes.stream()
                .mapToDouble(OutcomeDetailsDto::getValue)
                .sum();
    }

    private Map<String, List<OutcomeDetailsDto>> groupByCategory(final List<OutcomeDetailsDto> allOutcomes) {
        return allOutcomes.stream()
                .collect(Collectors.groupingBy(OutcomeDetailsDto::getCategory, Collectors.toList()));
    }

    private void createSummariesForEachCategory(final Map<String, List<OutcomeDetailsDto>> outcomesByCategory) {
        this.outcomesByCategories = outcomesByCategory.keySet().stream()
                .map(outcomesByCategory::get)
                .map(CategorySummaryDto::new)
                .collect(Collectors.toList());
    }

}
