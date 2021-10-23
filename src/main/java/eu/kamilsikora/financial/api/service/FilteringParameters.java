package eu.kamilsikora.financial.api.service;

import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.entity.expenses.Category;
import eu.kamilsikora.financial.api.entity.expenses.Expenses;
import eu.kamilsikora.financial.api.entity.expenses.OutcomeType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder(access = AccessLevel.PACKAGE)
public class FilteringParameters {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final OutcomeType type;
    private final List<Category> categories;
    private final Expenses expenses;
    private final User user;
}
