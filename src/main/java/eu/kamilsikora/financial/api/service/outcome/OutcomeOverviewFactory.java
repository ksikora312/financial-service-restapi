package eu.kamilsikora.financial.api.service.outcome;

import eu.kamilsikora.financial.api.entity.expenses.OutcomeType;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OutcomeOverviewFactory {
    private final List<OverviewProvider> overviewProviders;

    public OverviewProvider forType(OutcomeType outcomeType) {
        for(final OverviewProvider overviewProvider: overviewProviders) {
            if(overviewProvider.canHandle(outcomeType))
                return overviewProvider;
        }
        throw new IllegalArgumentException();
    }
}
