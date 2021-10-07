package eu.kamilsikora.financial.api.dto.list.todo;

import com.fasterxml.jackson.annotation.JsonFormat;
import eu.kamilsikora.financial.api.ApplicationConstants;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewToDoListElement {
    private Long listId;
    private String name;
    private String description;
    @JsonFormat(pattern = ApplicationConstants.DATE_FORMAT)
    private LocalDateTime dueDate;
    private String priority;
}
