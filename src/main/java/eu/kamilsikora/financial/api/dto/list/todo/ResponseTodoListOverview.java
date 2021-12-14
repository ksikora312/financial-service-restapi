package eu.kamilsikora.financial.api.dto.list.todo;

import lombok.Data;

@Data
public class ResponseTodoListOverview {
    private Long listId;
    private String name;
    private Boolean isPrimary;
}
