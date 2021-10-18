package eu.kamilsikora.financial.api.controller.outcome;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.dto.outcome.OutcomeDetailsDto;
import eu.kamilsikora.financial.api.dto.outcome.UpdateOutcomeDto;
import eu.kamilsikora.financial.api.dto.outcome.continuity.ContinuityOutcomeDetailsDto;
import eu.kamilsikora.financial.api.dto.outcome.OutcomesOverviewDto;
import eu.kamilsikora.financial.api.dto.outcome.continuity.NewContinuityOutcomeDto;
import eu.kamilsikora.financial.api.dto.outcome.NewOutcomeDto;
import eu.kamilsikora.financial.api.dto.outcome.OutcomeSummaryDto;
import eu.kamilsikora.financial.api.dto.outcome.continuity.UpdateContinuityOutcomeDto;
import eu.kamilsikora.financial.api.entity.expenses.OutcomeType;
import eu.kamilsikora.financial.api.service.outcome.ContinuityOutcomeService;
import eu.kamilsikora.financial.api.service.outcome.OutcomeOverviewFactory;
import eu.kamilsikora.financial.api.service.outcome.OutcomeService;
import eu.kamilsikora.financial.api.service.outcome.RegularOutcomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OutcomeControllerImpl implements OutcomeController {

    private final RegularOutcomeService regularOutcomeService;
    private final OutcomeService outcomeService;
    private final ContinuityOutcomeService continuityOutcomeService;
    private final OutcomeOverviewFactory overviewFactory;

    @Override
    public void addRegularOutcome(final UserPrincipal userPrincipal, final NewOutcomeDto newOutcomeDto) {
        regularOutcomeService.addNewOutcome(userPrincipal, newOutcomeDto);
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
    public OutcomesOverviewDto getOverview(final UserPrincipal userPrincipal, final OutcomeType type) {
        return overviewFactory.forType(type).getOverview(userPrincipal);
    }

    @Override
    public ContinuityOutcomeDetailsDto getDetails(final UserPrincipal userPrincipal, final Long id) {
        return continuityOutcomeService.getDetails(userPrincipal, id);
    }

    @Override
    public OutcomeSummaryDto getOutcomesSummary(final UserPrincipal userPrincipal) {
        return outcomeService.getSummaryOfAllOutcomes(userPrincipal);
    }
}
