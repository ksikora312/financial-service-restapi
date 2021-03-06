package eu.kamilsikora.financial.api.dto.outcome;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategorySummaryDto {
    private String category;
    private Double moneySpent;
    private Integer outcomesSum;
    private List<Double> values;
    private List<Integer> outcomes;

    public CategorySummaryDto(final String category, final List<Double> values, final List<Integer> outcomes) {
        this.category = category;
        this.values = values;
        this.outcomes = outcomes;
        final String trimmedSumString = String.format("%.2f", values.stream().mapToDouble(Double::valueOf).sum()).replace(',', '.');
        this.moneySpent = Double.parseDouble(trimmedSumString);
        this.outcomesSum = outcomes.stream().mapToInt(Integer::valueOf).sum();
    }

}
