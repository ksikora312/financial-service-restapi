package eu.kamilsikora.financial.api.entity.expenses;

import eu.kamilsikora.financial.api.entity.list.shopping.ShoppingList;
import eu.kamilsikora.financial.api.entity.list.shopping.ShoppingListElement;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
public class ShoppingListSingleOutcome extends SingleOutcome {

    @OneToOne
    @JoinColumn(name = "source_id")
    private ShoppingList source;

}
