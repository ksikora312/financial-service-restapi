package eu.kamilsikora.financial.api.entity.expenses;

import eu.kamilsikora.financial.api.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "outcome")
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
    protected Long outcomeId;

    protected Integer value;
    protected Integer valueAfterDecimalPoint;
    protected OutcomeType outcomeType;
    protected LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "expenses_id")
    private Expenses expenses;
}
