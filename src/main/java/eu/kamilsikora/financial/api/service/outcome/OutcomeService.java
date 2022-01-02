package eu.kamilsikora.financial.api.service.outcome;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.dto.outcome.OutcomeDetailsDto;
import eu.kamilsikora.financial.api.dto.outcome.OutcomeSummaryDto;
import eu.kamilsikora.financial.api.dto.outcome.UpdateOutcomeDto;
import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.entity.expenses.Category;
import eu.kamilsikora.financial.api.entity.expenses.SingleOutcome;
import eu.kamilsikora.financial.api.errorhandling.ObjectDoesNotExistException;
import eu.kamilsikora.financial.api.mapper.OutcomeMapper;
import eu.kamilsikora.financial.api.repository.outcome.SingleOutcomeRepository;
import eu.kamilsikora.financial.api.service.UserHelperService;
import eu.kamilsikora.financial.api.validation.ExceptionThrowingValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OutcomeService {
    private final SingleOutcomeRepository outcomeRepository;
    private final UserHelperService userHelperService;
    private final CategoryService categoryService;
    private final ExceptionThrowingValidator validator;
    private final OutcomeMapper outcomeMapper;


    @Transactional
    public OutcomeDetailsDto updateOutcome(final UserPrincipal userPrincipal, final UpdateOutcomeDto updateOutcomeDto) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final SingleOutcome outcome = outcomeRepository.findByExpensesAndId(user.getExpenses(), updateOutcomeDto.getId()).
                orElseThrow(() -> new ObjectDoesNotExistException("Outcome does not exist!"));
        Category category = updateOutcomeDto.getCategoryId() != null?
                categoryService.resolveAndIncrementUsage(user, updateOutcomeDto.getCategoryId()): null;
        outcomeMapper.mapIntoSingleOutcome(outcome, updateOutcomeDto, category);
        validator.validate(outcome);
        outcomeRepository.save(outcome);
        return outcomeMapper.mapToDto(outcome);
    }

}