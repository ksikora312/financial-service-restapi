package eu.kamilsikora.financial.api.repository.outcome;

import eu.kamilsikora.financial.api.dto.FilteringParametersDto;
import eu.kamilsikora.financial.api.dto.outcome.OverviewType;
import eu.kamilsikora.financial.api.entity.expenses.OutcomeType;
import eu.kamilsikora.financial.api.entity.expenses.SingleOutcome;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class SingleOutcomeSpecification<T extends SingleOutcome> {

    private final FilteringParametersDto parameters;

    public Specification<T> buildOnParameters() {
        Specification<T> spec = expenses()
                .and(startDate())
                .and(endDate());
        if (parameters.getCategory() != null) {
            spec = spec.and(category());
        }
        if(parameters.getType() != OverviewType.ALL) {
            spec = spec.and(type());
        }
        return spec;
    }

    private Specification<T> expenses() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("expenses"), parameters.getExpenses());
    }

    private Specification<T> startDate() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("date"), parameters.getStartDate());
    }

    private Specification<T> endDate() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("date"), parameters.getEndDate());
    }

    private Specification<T> type() {
        OutcomeType type = OutcomeType.REGULAR_OUTCOME;
        switch(parameters.getType()) {
            case CONTINUITY_SINGLE_OUTCOME:
                type = OutcomeType.CONTINUOUS_OUTCOME;
                break;
            case SHOPPING_LIST_SINGLE_OUTCOME:
                type = OutcomeType.SHOPPING_LIST_OUTCOME;
        }
        OutcomeType finalType = type;
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("outcomeType"), finalType);
    }

    private Specification<T> category() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category"), parameters.getCategory());
    }
}
