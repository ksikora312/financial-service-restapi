package eu.kamilsikora.financial.api.entity.expenses;

import eu.kamilsikora.financial.api.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import java.util.List;

@Entity
@Getter
@Setter
public class Expenses {
    @Id
    @SequenceGenerator(
            name = "expenses_seq",
            sequenceName = "expenses_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expenses_seq")
    protected Long expenseId;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "expenses", fetch = FetchType.LAZY)
    private List<SingleOutcome> outcomes;

}
