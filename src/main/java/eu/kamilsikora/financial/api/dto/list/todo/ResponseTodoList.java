package eu.kamilsikora.financial.api.dto.list.todo;

import lombok.Data;

import java.util.List;

@Data
public class ResponseTodoList {
    private Long listId;
    private String name;
    private List<ResponseTodoListElement> elements;
    private Boolean isPrimary;
}
