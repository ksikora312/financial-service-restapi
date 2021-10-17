package eu.kamilsikora.financial.api.service.outcome;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.dto.outcome.NewContinuityOutcomeDto;
import eu.kamilsikora.financial.api.dto.outcome.UpdateContinuityOutcomeDto;
import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.entity.expenses.Category;
import eu.kamilsikora.financial.api.entity.expenses.ContinuityOutcome;
import eu.kamilsikora.financial.api.entity.expenses.ContinuitySingleOutcome;
import eu.kamilsikora.financial.api.errorhandling.ObjectDoesNotExistException;
import eu.kamilsikora.financial.api.mapper.OutcomeMapper;
import eu.kamilsikora.financial.api.repository.outcome.ContinuityOutcomeRepository;
import eu.kamilsikora.financial.api.repository.outcome.ContinuitySingleOutcomeRepository;
import eu.kamilsikora.financial.api.service.UserHelperService;
import eu.kamilsikora.financial.api.validation.ExceptionThrowingValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContinuityOutcomeService {
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
        if(newContinuityOutcomeDto.getCreateOutcome()) {
            final ContinuitySingleOutcome continuitySingleOutcome = outcomeMapper.continuitySingleOutcome(continuityOutcome, user.getExpenses());
            continuitySingleOutcomeRepository.save(continuitySingleOutcome);
        }
    }

    public void updateContinuityOutcome(final UserPrincipal userPrincipal, final UpdateContinuityOutcomeDto updateContinuityOutcomeDto) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final ContinuityOutcome continuityOutcome = continuityOutcomeRepository.findByUserAndId(user, updateContinuityOutcomeDto.getId())
                .orElseThrow(() -> new ObjectDoesNotExistException("Continuity outcome does not exist!"));
        final Category category = categoryService.resolveAndIncrementUsage(user, updateContinuityOutcomeDto.getCategoryId());
        outcomeMapper.mapIntoContinuityOutcome(continuityOutcome, updateContinuityOutcomeDto, category);
        validator.validate(continuityOutcome, updateContinuityOutcomeDto);
        continuityOutcomeRepository.save(continuityOutcome);
   }

}
