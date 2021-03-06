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
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "SingleOutcome")
@Inheritance(strategy = InheritanceType.JOINED)
public class SingleOutcome {

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
    protected LocalDate date;
    @OneToOne
    @JoinColumn(name = "category_id")
    protected Category category;

    @ManyToOne
    @JoinColumn(name = "expenses_id")
    private Expenses expenses;
}
