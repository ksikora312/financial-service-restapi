package eu.kamilsikora.financial.api.entity.expenses;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class RegularSingleOutcome extends SingleOutcome {
    private String description;
}
