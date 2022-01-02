package eu.kamilsikora.financial.api.service.outcome;

import eu.kamilsikora.financial.api.dto.FilteringParametersDto;
import eu.kamilsikora.financial.api.dto.PageDto;
import eu.kamilsikora.financial.api.dto.outcome.OutcomeOverviewDto;
import eu.kamilsikora.financial.api.dto.outcome.OverviewType;
import eu.kamilsikora.financial.api.entity.User;
import org.springframework.data.domain.Page;

public interface OverviewProvider {
    boolean canHandle(OverviewType overviewType);

    Page<OutcomeOverviewDto> getOverview(User user, PageDto pageDto, FilteringParametersDto filteringParemeters);
}
