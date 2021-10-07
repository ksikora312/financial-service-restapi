package eu.kamilsikora.financial.api.repository.outcome;

import eu.kamilsikora.financial.api.entity.expenses.ContinuitySingleOutcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContinuitySingleOutcomeRepository extends JpaRepository<ContinuitySingleOutcome, Long> {
}
