package eu.kamilsikora.financial.api.repository.outcome;

import eu.kamilsikora.financial.api.entity.expenses.ContinuityOutcome;
import eu.kamilsikora.financial.api.entity.expenses.ContinuitySingleOutcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContinuitySingleOutcomeRepository extends JpaRepository<ContinuitySingleOutcome, Long> , JpaSpecificationExecutor<ContinuitySingleOutcome> {
    List<ContinuitySingleOutcome> findAllBySource(ContinuityOutcome source);
}
