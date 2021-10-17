package eu.kamilsikora.financial.api.dto.outcome.continuity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatedOutcomesDto {
    protected Long id;
    protected String name;
    protected Double value;
    protected String outcomeType;
    protected String date;
    protected String category;
}
