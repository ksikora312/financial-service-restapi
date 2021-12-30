package eu.kamilsikora.financial.api.service.outcome;

import eu.kamilsikora.financial.api.dto.FilteringParametersDto;
import eu.kamilsikora.financial.api.dto.outcome.OutcomesOverviewDto;
import eu.kamilsikora.financial.api.dto.outcome.OverviewType;
import eu.kamilsikora.financial.api.entity.User;

public interface OverviewProvider {
    boolean canHandle(OverviewType overviewType);

    OutcomesOverviewDto getOverview(User user, FilteringParametersDto filteringParemeters);
}
