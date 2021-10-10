package eu.kamilsikora.financial.api.dto.outcome;

import com.fasterxml.jackson.annotation.JsonFormat;
import eu.kamilsikora.financial.api.ApplicationConstants;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class NewOutcomeDto {
    private String item;
    private Double value;
    @JsonFormat(pattern = ApplicationConstants.DATE_FORMAT_WITHOUT_TIME, shape = JsonFormat.Shape.STRING)
    private Date date;
    private Long categoryId;
    private String description;
}
