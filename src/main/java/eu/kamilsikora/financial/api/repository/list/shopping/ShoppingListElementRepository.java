package eu.kamilsikora.financial.api.repository.list.shopping;

import eu.kamilsikora.financial.api.entity.list.shopping.ShoppingListElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingListElementRepository extends JpaRepository<ShoppingListElement, Long> {
}
