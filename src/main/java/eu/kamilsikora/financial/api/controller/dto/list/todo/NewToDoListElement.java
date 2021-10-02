package eu.kamilsikora.financial.api.controller.dto.list.todo;

import eu.kamilsikora.financial.api.ApplicationConstants;
import eu.kamilsikora.financial.api.entity.list.todo.Priority;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class NewToDoListElement {
    private Long listId;
    private String name;
    private String description;
    @DateTimeFormat(pattern = ApplicationConstants.DATE_FORMAT)
    private LocalDateTime dueDate;
    private Priority priority;
}
