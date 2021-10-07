package eu.kamilsikora.financial.api.entity.expenses;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class ContinuitySingleOutcome extends SingleOutcome {

    @ManyToOne
    @JoinColumn(name = "source_id")
    private ContinuityOutcome source;

}
