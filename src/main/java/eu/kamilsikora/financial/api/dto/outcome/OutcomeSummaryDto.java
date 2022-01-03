package eu.kamilsikora.financial.api.dto.outcome;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class OutcomeSummaryDto {
    private List<String> dates;
    private List<CategorySummaryDto> outcomesByCategories;
    private Double moneySpent;

    public OutcomeSummaryDto(final List<LocalDate> dates, final List<CategorySummaryDto> outcomesByCategories) {
        this.dates = dates.stream().map(LocalDate::toString).collect(Collectors.toList());
        this.outcomesByCategories = outcomesByCategories;
        this.moneySpent = outcomesByCategories.stream().mapToDouble(CategorySummaryDto::getMoneySpent).sum();
    }

}
