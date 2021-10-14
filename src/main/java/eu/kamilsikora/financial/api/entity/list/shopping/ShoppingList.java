package eu.kamilsikora.financial.api.entity.list.shopping;

import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.entity.expenses.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class ShoppingList {
    @Id
    @SequenceGenerator(
            name = "shopping_list_seq",
            sequenceName = "shopping_list_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shopping_list_seq")
    private Long listId;
    private String name;
    @OneToMany(mappedBy = "list")
    private List<ShoppingListElement> elements;
    private Boolean isPrimary;
    private Boolean done;
    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private LocalDateTime createdDate;
    private Double value;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ShoppingListElement addElement(ShoppingListElement newElement) {
        elements.add(newElement);
        return newElement;
    }
}
