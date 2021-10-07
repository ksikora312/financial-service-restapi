package eu.kamilsikora.financial.api.repository;

import eu.kamilsikora.financial.api.entity.expenses.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, Long> {
}
