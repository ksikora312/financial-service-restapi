package eu.kamilsikora.financial.api.dto.outcome;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatedOutcomesDto {
    protected Long outcomeId;
    protected String name;
    protected Double value;
    protected String outcomeType;
    protected String date;
    protected String category;
}
