package eu.kamilsikora.financial.api.dto.list.todo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResponseTodoListCollection {
    private List<ResponseTodoList> lists;
}
