package eu.kamilsikora.financial.api.entity.expenses;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class ContinuityOutcome {
    @Id
    @SequenceGenerator(
            name = "continuity_seq",
            sequenceName = "continuity_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "continuity_seq")
    private Long id;

    private LocalDateTime addedDate;
    private LocalDateTime updateDate;
    private Double value;
    private Integer timeIntervalInDays;
    @OneToMany(mappedBy = "source")
    private List<ContinuitySingleOutcome> producedOutcomes;

}
