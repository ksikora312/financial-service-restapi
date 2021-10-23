package eu.kamilsikora.financial.api.dto;

import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.entity.expenses.Category;
import eu.kamilsikora.financial.api.entity.expenses.Expenses;
import eu.kamilsikora.financial.api.entity.expenses.OutcomeType;
import eu.kamilsikora.financial.api.errorhandling.ObjectDoesNotExistException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class FilteringParametersDtoBuilder {

    public static FilteringParametersDto build(User user, LocalDate startDate, LocalDate endDate, OutcomeType type, Long categoryId) {
        Category category = null;
        if (categoryId != null) {
            category = user.getCategories().stream()
                    .filter(cat -> cat.getId().equals(categoryId))
                    .findFirst().orElseThrow(() -> new ObjectDoesNotExistException("Category does not exist!"));
        }
        final Expenses expenses = user.getExpenses();
        final LocalDate start = startDate == null ? defaultStartDate() : startDate;
        final LocalDate end = endDate == null ? defaultEndDate() : endDate;

        return FilteringParametersDto.builder()
                .category(category)
                .expenses(expenses)
                .startDate(start)
                .endDate(end)
                .user(user)
                .type(type)
                .build();
    }

    private static LocalDate defaultStartDate() {
        Instant now = Instant.now().truncatedTo(ChronoUnit.DAYS);
        Instant instantBeforeTimeFrame = now.minus(30, ChronoUnit.DAYS);
        return LocalDate.ofInstant(instantBeforeTimeFrame, ZoneId.systemDefault());
    }

    private static LocalDate defaultEndDate() {
        Instant now = Instant.now();
        return LocalDate.ofInstant(now, ZoneId.systemDefault());
    }

}
