package eu.kamilsikora.financial.api.entity.list.todo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewToDoListElement {
    private String name;
    private String description;
    private LocalDateTime dueDate;
    private Priority priority;
}
