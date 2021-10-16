package eu.kamilsikora.financial.api.service;

import eu.kamilsikora.financial.api.entity.expenses.ContinuityOutcome;
import eu.kamilsikora.financial.api.entity.expenses.ContinuitySingleOutcome;
import eu.kamilsikora.financial.api.mapper.OutcomeMapper;
import eu.kamilsikora.financial.api.repository.outcome.ContinuityOutcomeRepository;
import eu.kamilsikora.financial.api.repository.outcome.ContinuitySingleOutcomeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class AsyncSynchronizationService implements Runnable {

    private final ContinuityOutcomeRepository continuityOutcomeRepository;
    private final ContinuitySingleOutcomeRepository continuitySingleOutcomeRepository;
    private final OutcomeMapper outcomeMapper;

    @Override
    @Transactional
    public void run() {
        synchronizeContinuityOutcomes();
    }

    private void synchronizeContinuityOutcomes() {
        final List<ContinuityOutcome> continuityOutcomes =
                continuityOutcomeRepository.findAllRequiringOutcomeCreation(LocalDateTime.now());
        final List<ContinuitySingleOutcome> singleOutcomesToBeSaved = continuityOutcomes.stream()
                .map(this::processSingleContinuityOutcome)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        log.info("Saving " + singleOutcomesToBeSaved.size() + " single outcomes");
        continuitySingleOutcomeRepository.saveAll(singleOutcomesToBeSaved);
    }

    private List<ContinuitySingleOutcome> processSingleContinuityOutcome(final ContinuityOutcome continuityOutcome) {
        List<ContinuitySingleOutcome> outcomesToBeSaved = new ArrayList<>();
        while (LocalDateTime.now().isAfter(continuityOutcome.getNextUsage())) {
            final ContinuitySingleOutcome continuitySingleOutcome =
                    outcomeMapper.continuitySingleOutcome(continuityOutcome, continuityOutcome.getUser().getExpenses());
            outcomesToBeSaved.add(continuitySingleOutcome);
            continuityOutcome.setLastUsage(continuityOutcome.getLastUsage().plus(continuityOutcome.getTimeIntervalInDays(), ChronoUnit.DAYS));
            final LocalDateTime currentNextUsage = continuityOutcome.getNextUsage();
            final LocalDateTime newNextUsage = currentNextUsage.plus(continuityOutcome.getTimeIntervalInDays(), ChronoUnit.DAYS);
            continuityOutcome.setNextUsage(newNextUsage);
        }
        return outcomesToBeSaved;
    }
}
