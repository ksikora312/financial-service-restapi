package eu.kamilsikora.financial.api.dto.outcome;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class OutcomeSummaryDto {
    private String startDate;
    private String endDate;
    private List<String> dates;
    private List<CategorySummaryDto> outcomesByCategories;
    private Double moneySpent;
    private Integer totalOutcomes;

    public OutcomeSummaryDto(final String startDate, final String endDate, final List<LocalDate> dates, final List<CategorySummaryDto> outcomesByCategories) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.dates = dates.stream().map(LocalDate::toString).collect(Collectors.toList());
        this.outcomesByCategories = outcomesByCategories;
        this.moneySpent = outcomesByCategories.stream().mapToDouble(CategorySummaryDto::getMoneySpent).sum();
        this.totalOutcomes = outcomesByCategories.stream().mapToInt(CategorySummaryDto::getOutcomesSum).sum();
    }

}
