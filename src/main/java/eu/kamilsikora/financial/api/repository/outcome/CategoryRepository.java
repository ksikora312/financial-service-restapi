package eu.kamilsikora.financial.api.repository.outcome;

import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.entity.expenses.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByUserAndName(User user, String name);
    Optional<Category> findByUserAndId(User user, Long id);
}
