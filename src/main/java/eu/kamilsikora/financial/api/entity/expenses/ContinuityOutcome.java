package eu.kamilsikora.financial.api.entity.expenses;

import eu.kamilsikora.financial.api.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
    private LocalDateTime lastUsage;
    private Double value;
    private Integer timeIntervalInDays;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToMany(mappedBy = "source")
    private List<ContinuitySingleOutcome> producedOutcomes;

}
