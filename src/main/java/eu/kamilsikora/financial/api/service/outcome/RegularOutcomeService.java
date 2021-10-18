package eu.kamilsikora.financial.api.service.outcome;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.dto.outcome.NewOutcomeDto;
import eu.kamilsikora.financial.api.dto.outcome.OutcomesOverviewDto;
import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.entity.expenses.Category;
import eu.kamilsikora.financial.api.entity.expenses.OutcomeType;
import eu.kamilsikora.financial.api.entity.expenses.RegularSingleOutcome;
import eu.kamilsikora.financial.api.mapper.OutcomeMapper;
import eu.kamilsikora.financial.api.repository.outcome.RegularSingleOutcomeRepository;
import eu.kamilsikora.financial.api.service.UserHelperService;
import eu.kamilsikora.financial.api.validation.ExceptionThrowingValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegularOutcomeService implements OverviewProvider {
    private final RegularSingleOutcomeRepository outcomeRepository;
    private final UserHelperService userHelperService;
    private final CategoryService categoryService;
    private final OutcomeMapper outcomeMapper;
    private final ExceptionThrowingValidator validator;

    @Transactional
    public void addNewOutcome(final UserPrincipal userPrincipal, final NewOutcomeDto newOutcomeDto) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final Category category = categoryService.resolveAndIncrementUsage(user, newOutcomeDto.getCategoryId());
        final RegularSingleOutcome outcome = outcomeMapper.mapToEntity(newOutcomeDto, user, category);
        validator.validate(outcome, newOutcomeDto);
        outcomeRepository.save(outcome);
    }

    @Override
    public boolean canHandle(OutcomeType outcomeType) {
        return outcomeType == OutcomeType.REGULAR_OUTCOME;
    }

    @Override
    public OutcomesOverviewDto getOverview(UserPrincipal userPrincipal) {
        return null;
    }
}
