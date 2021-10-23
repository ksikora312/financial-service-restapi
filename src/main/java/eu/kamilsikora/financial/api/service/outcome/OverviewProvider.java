package eu.kamilsikora.financial.api.service.outcome;

import eu.kamilsikora.financial.api.dto.FilteringParametersDto;
import eu.kamilsikora.financial.api.dto.outcome.OutcomesOverviewDto;
import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.entity.expenses.OutcomeType;

public interface OverviewProvider {
    boolean canHandle(OutcomeType outcomeType);

    OutcomesOverviewDto getOverview(User user, FilteringParametersDto filteringParemeters);
}
