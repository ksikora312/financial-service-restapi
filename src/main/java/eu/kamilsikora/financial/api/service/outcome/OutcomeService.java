package eu.kamilsikora.financial.api.service.outcome;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.dto.outcome.OutcomeDetailsDto;
import eu.kamilsikora.financial.api.dto.outcome.OutcomeSummaryDto;
import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.entity.expenses.SingleOutcome;
import eu.kamilsikora.financial.api.mapper.OutcomeMapper;
import eu.kamilsikora.financial.api.repository.outcome.SingleOutcomeRepository;
import eu.kamilsikora.financial.api.service.UserHelperService;
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
    private final OutcomeMapper outcomeMapper;

    @Transactional(readOnly = true)
    public OutcomeSummaryDto getSummaryOfAllOutcomes(final UserPrincipal userPrincipal) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final List<SingleOutcome> allOutcomes = user.getExpenses().getOutcomes();
        final List<OutcomeDetailsDto> allOutcomesDto = allOutcomes.stream()
                .map(outcomeMapper::mapToDto)
                .collect(Collectors.toList());
        return new OutcomeSummaryDto(allOutcomesDto);
    }

}
