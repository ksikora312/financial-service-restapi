package eu.kamilsikora.financial.api.service.outcome;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.dto.outcome.OutcomesOverviewDto;
import eu.kamilsikora.financial.api.entity.expenses.OutcomeType;

public interface OverviewProvider {
    boolean canHandle(OutcomeType outcomeType);
    OutcomesOverviewDto getOverview(UserPrincipal userPrincipal);
}
