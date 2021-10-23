package eu.kamilsikora.financial.api.repository.outcome;

import eu.kamilsikora.financial.api.service.FilteringParameters;
import eu.kamilsikora.financial.api.entity.expenses.SingleOutcome;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class SingleOutcomeSpecification<T extends SingleOutcome> {

    private final FilteringParameters parameters;

    public Specification<T> buildOnParameters() {
        final Specification<T> spec = expenses()
                .and(startDate())
                .and(endDate())
                .and(type());
        if(parameters.getCategory() != null) {
            return spec.and(category());
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
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("outcomeType"), parameters.getType());
    }

    private Specification<T> category() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category"), parameters.getCategory());
    }

}
