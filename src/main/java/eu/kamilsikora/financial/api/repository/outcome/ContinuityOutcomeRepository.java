package eu.kamilsikora.financial.api.repository.outcome;

import eu.kamilsikora.financial.api.entity.expenses.ContinuityOutcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContinuityOutcomeRepository extends JpaRepository<ContinuityOutcome, Long> {
    @Query("SELECT c FROM ContinuityOutcome c WHERE c.nextUsage < ?1")
    List<ContinuityOutcome> findAllRequiringOutcomeCreation(LocalDateTime now);
}
