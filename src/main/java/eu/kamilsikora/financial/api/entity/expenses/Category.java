package eu.kamilsikora.financial.api.entity.expenses;

import eu.kamilsikora.financial.api.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @SequenceGenerator(
            name = "category_seq",
            sequenceName = "category_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
    private Long id;
    private String name;
    private Integer usages;
    @ManyToOne
    @JoinColumn(name = "expenses_id")
    private User user;

    public void incrementUsages() {
        this.usages++;
    }
}
