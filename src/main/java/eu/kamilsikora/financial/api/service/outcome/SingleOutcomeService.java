package eu.kamilsikora.financial.api.service.outcome;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.dto.FilteringParametersDto;
import eu.kamilsikora.financial.api.dto.PageDto;
import eu.kamilsikora.financial.api.dto.outcome.NewOutcomeDto;
import eu.kamilsikora.financial.api.dto.outcome.OutcomeOverviewDto;
import eu.kamilsikora.financial.api.dto.outcome.OverviewType;
import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.entity.expenses.Category;
import eu.kamilsikora.financial.api.entity.expenses.RegularSingleOutcome;
import eu.kamilsikora.financial.api.entity.expenses.SingleOutcome;
import eu.kamilsikora.financial.api.mapper.OutcomeMapper;
import eu.kamilsikora.financial.api.repository.outcome.RegularSingleOutcomeRepository;
import eu.kamilsikora.financial.api.repository.outcome.SingleOutcomeRepository;
import eu.kamilsikora.financial.api.repository.outcome.SingleOutcomeSpecification;
import eu.kamilsikora.financial.api.service.UserHelperService;
import eu.kamilsikora.financial.api.validation.ExceptionThrowingValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SingleOutcomeService implements OverviewProvider {
    private final RegularSingleOutcomeRepository regularSingleOutcomeRepository;
    private final SingleOutcomeRepository singleOutcomeRepository;
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
        regularSingleOutcomeRepository.save(outcome);
    }

    @Override
    public boolean canHandle(final OverviewType overviewType) {
        return (overviewType == OverviewType.REGULAR_SINGLE_OUTCOME ||
                overviewType == OverviewType.CONTINUITY_SINGLE_OUTCOME ||
                overviewType == OverviewType.SHOPPING_LIST_SINGLE_OUTCOME ||
                overviewType == OverviewType.ALL);
    }

    @Override
    public Page<OutcomeOverviewDto> getOverview(final User user, final PageDto page, final FilteringParametersDto filteringParameters) {
        final Specification<SingleOutcome> specification =
                new SingleOutcomeSpecification<>(filteringParameters).buildOnParameters();
        final Pageable pageable = PageRequest.of(page.getPageNumber(), page.getPageSize(), Sort.by("date").descending().and(Sort.by("value").descending()));
        Page<SingleOutcome> outcomesPage = singleOutcomeRepository.findAll(specification, pageable);
        return outcomesPage.map(outcomeMapper::mapToOverviewDto);
    }
}
