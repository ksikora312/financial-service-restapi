package eu.kamilsikora.financial.api.repository.list.shopping;

import eu.kamilsikora.financial.api.entity.list.shopping.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {
}
