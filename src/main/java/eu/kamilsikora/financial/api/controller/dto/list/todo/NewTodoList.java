package eu.kamilsikora.financial.api.controller.dto.list.todo;

import lombok.Data;

@Data
public class NewTodoList {
    private String name;
    private Boolean isPrimary;
}
