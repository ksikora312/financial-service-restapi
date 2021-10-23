package eu.kamilsikora.financial.api.dto;

import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.entity.expenses.Category;
import eu.kamilsikora.financial.api.entity.expenses.Expenses;
import eu.kamilsikora.financial.api.entity.expenses.OutcomeType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder(access = AccessLevel.PACKAGE)
public class FilteringParametersDto {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final OutcomeType type;
    private final Category category;
    private final Expenses expenses;
    private final User user;
}
