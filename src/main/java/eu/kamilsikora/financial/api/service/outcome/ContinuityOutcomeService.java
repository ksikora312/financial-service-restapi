package eu.kamilsikora.financial.api.service.outcome;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.dto.outcome.OutcomeOverviewDto;
import eu.kamilsikora.financial.api.dto.outcome.OutcomesOverviewDto;
import eu.kamilsikora.financial.api.dto.outcome.continuity.ContinuityOutcomeDetailsDto;
import eu.kamilsikora.financial.api.dto.outcome.continuity.NewContinuityOutcomeDto;
import eu.kamilsikora.financial.api.dto.outcome.continuity.UpdateContinuityOutcomeDto;
import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.entity.expenses.Category;
import eu.kamilsikora.financial.api.entity.expenses.ContinuityOutcome;
import eu.kamilsikora.financial.api.entity.expenses.ContinuitySingleOutcome;
import eu.kamilsikora.financial.api.entity.expenses.OutcomeType;
import eu.kamilsikora.financial.api.errorhandling.ObjectDoesNotExistException;
import eu.kamilsikora.financial.api.mapper.OutcomeMapper;
import eu.kamilsikora.financial.api.repository.outcome.ContinuityOutcomeRepository;
import eu.kamilsikora.financial.api.repository.outcome.ContinuityOutcomeSpecification;
import eu.kamilsikora.financial.api.repository.outcome.ContinuitySingleOutcomeRepository;
import eu.kamilsikora.financial.api.service.FilteringParameters;
import eu.kamilsikora.financial.api.service.FilteringParametersBuilder;
import eu.kamilsikora.financial.api.service.UserHelperService;
import eu.kamilsikora.financial.api.validation.ExceptionThrowingValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContinuityOutcomeService implements OverviewProvider {
    private final OutcomeMapper outcomeMapper;
    private final ContinuityOutcomeRepository continuityOutcomeRepository;
    private final ContinuitySingleOutcomeRepository continuitySingleOutcomeRepository;
    private final UserHelperService userHelperService;
    private final CategoryService categoryService;
    private final ExceptionThrowingValidator validator;

    @Transactional
    public void createContinuityOutcome(final UserPrincipal userPrincipal, final NewContinuityOutcomeDto newContinuityOutcomeDto) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final Category category = categoryService.resolveAndIncrementUsage(user, newContinuityOutcomeDto.getCategoryId());
        final ContinuityOutcome continuityOutcome = outcomeMapper.mapToEntity(newContinuityOutcomeDto, user, category);
        validator.validate(continuityOutcome, newContinuityOutcomeDto);
        continuityOutcomeRepository.save(continuityOutcome);
        if (newContinuityOutcomeDto.getCreateOutcome()) {
            final ContinuitySingleOutcome continuitySingleOutcome = outcomeMapper.continuitySingleOutcome(continuityOutcome, user.getExpenses());
            continuitySingleOutcomeRepository.save(continuitySingleOutcome);
        }
    }

    @Transactional
    public void updateContinuityOutcome(final UserPrincipal userPrincipal, final UpdateContinuityOutcomeDto updateContinuityOutcomeDto) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final ContinuityOutcome continuityOutcome = continuityOutcomeRepository.findByUserAndId(user, updateContinuityOutcomeDto.getId())
                .orElseThrow(() -> new ObjectDoesNotExistException("Continuity outcome does not exist!"));
        final Category category = categoryService.resolveAndIncrementUsage(user, updateContinuityOutcomeDto.getCategoryId());
        outcomeMapper.mapIntoContinuityOutcome(continuityOutcome, updateContinuityOutcomeDto, category);
        validator.validate(continuityOutcome);
        continuityOutcomeRepository.save(continuityOutcome);
    }

    @Override
    @Transactional(readOnly = true)
    public OutcomesOverviewDto getOverview(final UserPrincipal userPrincipal, final OutcomeType type, final LocalDate startDate, final LocalDate endDate, final Long category) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final FilteringParameters filteringParameters = new FilteringParametersBuilder(startDate, endDate, type, category, user).build();
        final Specification<ContinuityOutcome> specification = new ContinuityOutcomeSpecification(filteringParameters).buildOnParameters();
        final List<ContinuityOutcome> continuityOutcomes = continuityOutcomeRepository.findAll(specification);
        final List<OutcomeOverviewDto> continuityOutcomesDto = continuityOutcomes.stream()
                .map(outcomeMapper::mapToOverviewDto)
                .collect(Collectors.toList());
        return new OutcomesOverviewDto(continuityOutcomesDto);
    }

    @Transactional(readOnly = true)
    public ContinuityOutcomeDetailsDto getDetails(final UserPrincipal userPrincipal, final Long id) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final ContinuityOutcome continuityOutcome = continuityOutcomeRepository.
                findByUserAndId(user, id).orElseThrow(() -> new ObjectDoesNotExistException("Continuity outcome does not exist!"));
        return outcomeMapper.mapToDetailsDto(continuityOutcome);
    }

    @Override
    public boolean canHandle(OutcomeType outcomeType) {
        return outcomeType == OutcomeType.CONTINUOUS_OUTCOME;
    }
}
