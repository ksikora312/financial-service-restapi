package eu.kamilsikora.financial.api.dto.outcome;

import com.fasterxml.jackson.annotation.JsonFormat;
import eu.kamilsikora.financial.api.ApplicationConstants;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NewOutcomeDto {
    private String item;
    private Double value;
    private String outcomeType;
    @JsonFormat(pattern = ApplicationConstants.DATE_FORMAT_WITHOUT_TIME)
    private LocalDateTime date;
    private Long categoryId;
    private String description;
}
