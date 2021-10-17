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
import eu.kamilsikora.financial.api.dto.outcome.category.CategoriesDto;
import eu.kamilsikora.financial.api.service.outcome.CategoryService;
import eu.kamilsikora.financial.api.service.outcome.ContinuityOutcomeService;
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
    // TODO: split into category controller
    private final CategoryService categoryService;

    @Override
    public void addRegularOutcome(final UserPrincipal userPrincipal, final NewOutcomeDto newOutcomeDto) {
        regularOutcomeService.addNewOutcome(userPrincipal, newOutcomeDto);
    }

    @Override
    public void addContinuityOutcome(UserPrincipal userPrincipal, NewContinuityOutcomeDto newContinuityOutcomeDto) {
        continuityOutcomeService.createContinuityOutcome(userPrincipal, newContinuityOutcomeDto);
    }

    @Override
    public void updateContinuityOutcome(UserPrincipal userPrincipal, UpdateContinuityOutcomeDto updateContinuityOutcomeDto) {
        continuityOutcomeService.updateContinuityOutcome(userPrincipal, updateContinuityOutcomeDto);
    }

    @Override
    public OutcomeDetailsDto updateOutcome(UserPrincipal userPrincipal, UpdateOutcomeDto updateOutcomeDto) {
        return outcomeService.updateOutcome(userPrincipal, updateOutcomeDto);
    }

    @Override
    public OutcomesOverviewDto getOverview(UserPrincipal userPrincipal) {
        return continuityOutcomeService.getOverview(userPrincipal);
    }

    @Override
    public ContinuityOutcomeDetailsDto getDetails(UserPrincipal userPrincipal, Long id) {
        return continuityOutcomeService.getDetails(userPrincipal, id);
    }

    @Override
    public void addCategory(final UserPrincipal userPrincipal, final String category) {
        categoryService.addNewCategory(userPrincipal, category);
    }

    @Override
    public CategoriesDto getAllCategories(final UserPrincipal userPrincipal) {
        return categoryService.getAllCategories(userPrincipal);
    }

    @Override
    public OutcomeSummaryDto getOutcomesSummary(UserPrincipal userPrincipal) {
        return outcomeService.getSummaryOfAllOutcomes(userPrincipal);
    }
}
