package eu.kamilsikora.financial.api.controller.list.todo;

import eu.kamilsikora.financial.api.controller.dto.list.todo.NewToDoListElement;
import eu.kamilsikora.financial.api.controller.dto.list.todo.NewTodoList;
import eu.kamilsikora.financial.api.controller.dto.list.todo.ResponseTodoList;
import eu.kamilsikora.financial.api.service.list.todo.TodoListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TodoListControllerImpl implements TodoListController {

    private final TodoListService todoListService;

    @Override
    public ResponseTodoList createNewList(UserDetails user, NewTodoList newTodoList) {
        return todoListService.createNewList(user.getUsername(), newTodoList);
    }

    @Override
    public ResponseTodoList addNewElement(UserDetails user, NewToDoListElement newToDoListElement) {
        return todoListService.addNewElement(user.getUsername(), newToDoListElement);
    }
}
