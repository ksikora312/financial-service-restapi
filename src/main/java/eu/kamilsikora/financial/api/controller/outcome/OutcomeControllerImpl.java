package eu.kamilsikora.financial.api.controller.outcome;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.dto.FilteringParametersDto;
import eu.kamilsikora.financial.api.dto.FilteringParametersDtoBuilder;
import eu.kamilsikora.financial.api.dto.PageDto;
import eu.kamilsikora.financial.api.dto.outcome.NewOutcomeDto;
import eu.kamilsikora.financial.api.dto.outcome.OutcomeDetailsDto;
import eu.kamilsikora.financial.api.dto.outcome.OutcomeOverviewDto;
import eu.kamilsikora.financial.api.dto.outcome.OutcomeSummaryDto;
import eu.kamilsikora.financial.api.dto.outcome.OverviewType;
import eu.kamilsikora.financial.api.dto.outcome.UpdateOutcomeDto;
import eu.kamilsikora.financial.api.dto.outcome.continuity.ContinuityOutcomeDetailsDto;
import eu.kamilsikora.financial.api.dto.outcome.continuity.NewContinuityOutcomeDto;
import eu.kamilsikora.financial.api.dto.outcome.continuity.UpdateContinuityOutcomeDto;
import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.service.UserHelperService;
import eu.kamilsikora.financial.api.service.outcome.ContinuityOutcomeService;
import eu.kamilsikora.financial.api.service.outcome.OutcomeOverviewFactory;
import eu.kamilsikora.financial.api.service.outcome.OutcomeService;
import eu.kamilsikora.financial.api.service.outcome.SingleOutcomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class OutcomeControllerImpl implements OutcomeController {

    private final SingleOutcomeService singleOutcomeService;
    private final OutcomeService outcomeService;
    private final ContinuityOutcomeService continuityOutcomeService;
    private final UserHelperService userHelperService;
    private final OutcomeOverviewFactory overviewFactory;


    @Override
    public void addRegularOutcome(final UserPrincipal userPrincipal, final NewOutcomeDto newOutcomeDto) {
        singleOutcomeService.addNewOutcome(userPrincipal, newOutcomeDto);
    }

    @Override
    public void addContinuityOutcome(final UserPrincipal userPrincipal, final NewContinuityOutcomeDto newContinuityOutcomeDto) {
        continuityOutcomeService.createContinuityOutcome(userPrincipal, newContinuityOutcomeDto);
    }

    @Override
    public void updateContinuityOutcome(final UserPrincipal userPrincipal, final UpdateContinuityOutcomeDto updateContinuityOutcomeDto) {
        continuityOutcomeService.updateContinuityOutcome(userPrincipal, updateContinuityOutcomeDto);
    }

    @Override
    public OutcomeDetailsDto updateOutcome(final UserPrincipal userPrincipal, final UpdateOutcomeDto updateOutcomeDto) {
        return outcomeService.updateOutcome(userPrincipal, updateOutcomeDto);
    }

    @Override
    public Page<OutcomeOverviewDto> getOverview(final UserPrincipal userPrincipal, final OverviewType type,
                                                final Integer pageNumber, final Integer pageSize,
                                                final LocalDate startDate, final LocalDate endDate, final Long category) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final PageDto page = new PageDto(pageNumber, pageSize);
        final FilteringParametersDto parameters = FilteringParametersDtoBuilder.build(user, startDate, endDate, type, category);
        return overviewFactory.forType(type).getOverview(user, page, parameters);
    }

    @Override
    public ContinuityOutcomeDetailsDto getDetails(final UserPrincipal userPrincipal, final Long id) {
        return continuityOutcomeService.getDetails(userPrincipal, id);
    }

    @Override
    public OutcomeSummaryDto getOutcomesSummary(UserPrincipal userPrincipal, OverviewType type, LocalDate startDate, LocalDate endDate, Long category) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final FilteringParametersDto parameters = FilteringParametersDtoBuilder.build(user, startDate, endDate, type, category);
        return singleOutcomeService.provideSummary(user, parameters);
    }

    @Override
    public Page<OutcomeOverviewDto> getOutcomesProducedByContinuityOutcome(UserPrincipal userPrincipal, Long continuityOutcomeId, Integer pageNumber, Integer pageSize) {
        return singleOutcomeService.outcomesByContinuityOutcome(userPrincipal, continuityOutcomeId, pageNumber, pageSize);
    }
}
