package eu.kamilsikora.financial.api.controller.dto.list.todo;

import com.fasterxml.jackson.annotation.JsonFormat;
import eu.kamilsikora.financial.api.ApplicationConstants;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseTodoListElement {
    private Long elementId;
    private String name;
    private String description;
    @JsonFormat(pattern = ApplicationConstants.DATE_FORMAT)
    private LocalDateTime addedDate;
    @JsonFormat(pattern = ApplicationConstants.DATE_FORMAT)
    private LocalDateTime dueDate;
    private String priority;
    private Boolean done;

}
