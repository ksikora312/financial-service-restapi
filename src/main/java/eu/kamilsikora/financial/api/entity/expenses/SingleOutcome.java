package eu.kamilsikora.financial.api.entity.expenses;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import java.util.Date;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class SingleOutcome {

    @Id
    @SequenceGenerator(
            name = "outcome_seq",
            sequenceName = "outcome_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "outcome_seq")
    @Column(name = "outcome_id")
    protected Long id;
    protected String name;
    protected Double value;
    protected OutcomeType outcomeType;
    protected Date date;
    @OneToOne
    @JoinColumn(name = "category_id")
    protected Category category;

    @ManyToOne
    @JoinColumn(name = "expenses_id")
    private Expenses expenses;
}
