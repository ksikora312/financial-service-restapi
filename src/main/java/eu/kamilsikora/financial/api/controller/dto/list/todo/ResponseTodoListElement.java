package eu.kamilsikora.financial.api.controller.dto.list.todo;

import lombok.Data;

@Data
public class ResponseTodoListElement {
    private Long elementId;
    private String name;
    private String description;
    private String addedDate;
    private String dueDate;
    private String priority;
    private Boolean done;

}
