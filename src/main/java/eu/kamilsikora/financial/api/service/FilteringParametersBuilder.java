package eu.kamilsikora.financial.api.service;

import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.entity.expenses.Category;
import eu.kamilsikora.financial.api.entity.expenses.Expenses;
import eu.kamilsikora.financial.api.entity.expenses.OutcomeType;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class FilteringParametersBuilder {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final OutcomeType type;
    private final List<Long> categoryIds;
    private final User user;

    public FilteringParametersBuilder(LocalDate startDate, LocalDate endDate, OutcomeType type, List<Long> categoryIds, User user) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.categoryIds = categoryIds == null ? List.of() : categoryIds;
        this.user = user;
    }

    public FilteringParameters build() {
        final List<Category> categories = user.getCategories().stream()
                .filter(cat -> categoryIds.contains(cat.getId()))
                .collect(Collectors.toList());
        final Expenses expenses = user.getExpenses();
        final LocalDate start = startDate == null ? defaultStartDate() : startDate;
        final LocalDate end = endDate == null ? defaultEndDate() : endDate;

        return FilteringParameters.builder()
                .categories(categories)
                .expenses(expenses)
                .startDate(start)
                .endDate(end)
                .user(user)
                .type(type)
                .build();
    }

    private LocalDate defaultStartDate() {
        Instant now = Instant.now().truncatedTo(ChronoUnit.DAYS);
        Instant instantBeforeTimeFrame = now.minus(30, ChronoUnit.DAYS);
        return LocalDate.ofInstant(instantBeforeTimeFrame, ZoneId.systemDefault());
    }

    private LocalDate defaultEndDate() {
        Instant now = Instant.now();
        return LocalDate.ofInstant(now, ZoneId.systemDefault());
    }

}
