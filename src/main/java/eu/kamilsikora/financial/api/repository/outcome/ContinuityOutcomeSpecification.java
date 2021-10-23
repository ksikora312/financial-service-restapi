package eu.kamilsikora.financial.api.repository.outcome;

import eu.kamilsikora.financial.api.entity.expenses.ContinuityOutcome;
import eu.kamilsikora.financial.api.service.FilteringParameters;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
public class ContinuityOutcomeSpecification {

    private final FilteringParameters parameters;

    public Specification<ContinuityOutcome> buildOnParameters() {
        final Specification<ContinuityOutcome> spec = user()
                .and(active())
                .and(startDate())
                .and(endDate());
        if (parameters.getCategory() != null) {
            return spec.and(category());
        }
        return spec;
    }

    private Specification<ContinuityOutcome> user() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user"), parameters.getUser());
    }

    private Specification<ContinuityOutcome> startDate() {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.asc(root.get("lastUsage")));
            return criteriaBuilder.greaterThanOrEqualTo(root.get("lastUsage"), LocalDateTime.of(parameters.getStartDate(), atStartOfDay()));
        };
    }

    private Specification<ContinuityOutcome> endDate() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("lastUsage"), LocalDateTime.of(parameters.getEndDate(), atEndOfDay()));
    }

    private Specification<ContinuityOutcome> category() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category"), parameters.getCategory());
    }

    private Specification<ContinuityOutcome> active() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("active"), true);
    }

    private LocalTime atStartOfDay() {
        return LocalTime.of(0, 0, 0);
    }

    private LocalTime atEndOfDay() {
        return LocalTime.of(23, 59, 59);
    }
}
