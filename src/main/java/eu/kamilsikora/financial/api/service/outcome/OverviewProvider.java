package eu.kamilsikora.financial.api.service.outcome;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.dto.outcome.OutcomesOverviewDto;
import eu.kamilsikora.financial.api.entity.expenses.OutcomeType;

import java.time.LocalDate;
import java.util.List;

public interface OverviewProvider {
    boolean canHandle(OutcomeType outcomeType);

    OutcomesOverviewDto getOverview(UserPrincipal userPrincipal, OutcomeType type, LocalDate startDate, LocalDate endDate, List<Long> categories);
}
