package eu.kamilsikora.financial.api.list.todo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewToDoListElement {
    private String name;
    private String description;
    private LocalDateTime dueDate;
    private Priority priority;
}
