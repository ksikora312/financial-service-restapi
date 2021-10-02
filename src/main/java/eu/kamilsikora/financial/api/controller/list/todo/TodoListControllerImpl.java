package eu.kamilsikora.financial.api.controller.list.todo;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.controller.dto.list.todo.NewToDoListElement;
import eu.kamilsikora.financial.api.controller.dto.list.todo.NewTodoList;
import eu.kamilsikora.financial.api.controller.dto.list.todo.ResponseTodoList;
import eu.kamilsikora.financial.api.service.list.todo.TodoListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TodoListControllerImpl implements TodoListController {

    private final TodoListService todoListService;

    @Override
    public ResponseTodoList createNewList(UserPrincipal userPrincipal, NewTodoList newTodoList) {
        return todoListService.createNewList(userPrincipal, newTodoList);
    }

    @Override
    public ResponseTodoList addNewElement(UserPrincipal userPrincipal, NewToDoListElement newToDoListElement) {
        return todoListService.addNewElement(userPrincipal, newToDoListElement);
    }
}
