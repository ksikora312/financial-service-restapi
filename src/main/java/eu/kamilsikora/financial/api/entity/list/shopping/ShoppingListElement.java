package eu.kamilsikora.financial.api.entity.list.shopping;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ShoppingListElement {

    @Id
    @SequenceGenerator(
            name = "shopping_list_elem_seq",
            sequenceName = "shopping_list_elem_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shopping_list_elem_seq")
    private Long elementId;
    private String name;
    private Double value;
    private LocalDateTime addedDate;
    private Boolean done;

    @ManyToOne
    @JoinColumn(name = "list_id")
    private ShoppingList list;
}
