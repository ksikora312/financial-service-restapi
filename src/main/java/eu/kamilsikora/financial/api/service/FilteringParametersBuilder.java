package eu.kamilsikora.financial.api.service;

import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.entity.expenses.Category;
import eu.kamilsikora.financial.api.entity.expenses.Expenses;
import eu.kamilsikora.financial.api.entity.expenses.OutcomeType;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class FilteringParametersBuilder {
    private final Date startDate;
    private final Date endDate;
    private final OutcomeType type;
    private final List<Long> categoryIds;
    private final User user;

    public FilteringParameters build() {
        final List<Category> categories = user.getCategories().stream()
                .filter(cat -> categoryIds.contains(cat.getId()))
                .collect(Collectors.toList());
        final Expenses expenses = user.getExpenses();
        final Date start = startDate == null ? defaultStartDate() : startDate;
        final Date end = endDate == null ? defaultEndDate() : endDate;

        return FilteringParameters.builder()
                .categories(categories)
                .expenses(expenses)
                .startDate(start)
                .endDate(end)
                .user(user)
                .type(type)
                .build();
    }

    private Date defaultStartDate() {
        Instant now = Instant.now().truncatedTo(ChronoUnit.DAYS);
        Instant instantBeforeTimeFrame = now.minus(30, ChronoUnit.DAYS);
        return new Date(instantBeforeTimeFrame.toEpochMilli());
    }

    private Date defaultEndDate() {
        Instant now = Instant.now();
        return new Date(now.toEpochMilli());
    }

}
