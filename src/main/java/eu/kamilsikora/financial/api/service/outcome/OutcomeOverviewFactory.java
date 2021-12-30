package eu.kamilsikora.financial.api.service.outcome;

import eu.kamilsikora.financial.api.dto.outcome.OverviewType;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OutcomeOverviewFactory {
    private final List<OverviewProvider> overviewProviders;

    public OverviewProvider forType(OverviewType overviewType) {
        for (final OverviewProvider overviewProvider : overviewProviders) {
            if (overviewProvider.canHandle(overviewType))
                return overviewProvider;
        }
        throw new IllegalArgumentException("Provider for given type does not exist!");
    }
}

