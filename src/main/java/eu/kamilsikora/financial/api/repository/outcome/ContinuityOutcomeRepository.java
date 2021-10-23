package eu.kamilsikora.financial.api.repository.outcome;

import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.entity.expenses.ContinuityOutcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContinuityOutcomeRepository extends JpaRepository<ContinuityOutcome, Long>, JpaSpecificationExecutor<ContinuityOutcome> {
    @Query("SELECT c FROM ContinuityOutcome c WHERE c.nextUsage < ?1 and c.active = true")
    List<ContinuityOutcome> findAllRequiringOutcomeCreation(LocalDateTime now);
    Optional<ContinuityOutcome> findByUserAndId(User user, Long id);
    List<ContinuityOutcome> findByUserAndActive(User user, Boolean active);
}
