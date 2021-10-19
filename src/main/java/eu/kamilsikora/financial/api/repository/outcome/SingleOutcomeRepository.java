package eu.kamilsikora.financial.api.repository.outcome;

import eu.kamilsikora.financial.api.entity.expenses.Expenses;
import eu.kamilsikora.financial.api.entity.expenses.SingleOutcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SingleOutcomeRepository extends JpaRepository<SingleOutcome, Long>, JpaSpecificationExecutor<SingleOutcome> {
    Optional<SingleOutcome> findByExpensesAndId(Expenses expenses, Long id);
}
