package eu.kamilsikora.financial.api.dto.list.shopping;

import com.fasterxml.jackson.annotation.JsonFormat;
import eu.kamilsikora.financial.api.ApplicationConstants;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ShoppingListToOutcomeDto {
    private Long listId;
    private String name;
    private Double value;
    @JsonFormat(pattern = ApplicationConstants.DATE_FORMAT_WITHOUT_TIME, shape = JsonFormat.Shape.STRING)
    private Date date;
    private Long categoryId;
}
