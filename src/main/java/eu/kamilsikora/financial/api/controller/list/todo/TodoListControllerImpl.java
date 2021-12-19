package eu.kamilsikora.financial.api.controller.list.todo;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.dto.list.todo.NewToDoListElement;
import eu.kamilsikora.financial.api.dto.list.todo.NewTodoList;
import eu.kamilsikora.financial.api.dto.list.todo.ResponseTodoList;
import eu.kamilsikora.financial.api.dto.list.todo.ResponseTodoListCollection;
import eu.kamilsikora.financial.api.dto.list.todo.ResponseTodoListCollectionOverview;
import eu.kamilsikora.financial.api.dto.list.todo.ResponseTodoListElement;
import eu.kamilsikora.financial.api.service.list.todo.TodoListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TodoListControllerImpl implements TodoListController {

    private final TodoListService todoListService;

    @Override
    public ResponseTodoList createNewList(final UserPrincipal userPrincipal, final NewTodoList newTodoList) {
        return todoListService.createNewList(userPrincipal, newTodoList);
    }

    @Override
    public ResponseTodoList addNewElement(final UserPrincipal userPrincipal, final NewToDoListElement newToDoListElement) {
        return todoListService.addNewElement(userPrincipal, newToDoListElement);
    }

    @Override
    public ResponseTodoList changeListName(UserPrincipal userPrincipal, Long listId, String listName) {
        return todoListService.changeListName(userPrincipal, listId, listName);
    }

    @Override
    public ResponseTodoListElement markElementAs(final UserPrincipal userPrincipal, final Long elementId, final Boolean done) {
        return todoListService.markElementAs(userPrincipal, elementId, done);
    }

    @Override
    public ResponseTodoList getPrimaryList(final UserPrincipal userPrincipal) {
        return todoListService.getPrimaryList(userPrincipal);
    }

    @Override
    public ResponseTodoListCollectionOverview getListsOverview(UserPrincipal userPrincipal) {
        return todoListService.getListsOverview(userPrincipal);
    }

    @Override
    public ResponseTodoList getListAtId(UserPrincipal userPrincipal, Long listId) {
        return todoListService.getListAtId(userPrincipal, listId);
    }

    @Override
    public ResponseTodoListCollection getAllLists(final UserPrincipal userPrincipal) {
        return todoListService.getTodoLists(userPrincipal);
    }

    @Override
    public ResponseTodoList markAsPrimary(final UserPrincipal userPrincipal, final Long listId) {
        return todoListService.markAsPrimary(userPrincipal, listId);
    }

    @Override
    public void deleteList(UserPrincipal userPrincipal, Long listId) {
        todoListService.deleteList(userPrincipal, listId);
    }

    @Override
    public ResponseTodoList deleteListElement(UserPrincipal userPrincipal, Long elementId) {
        return todoListService.deleteListElement(userPrincipal, elementId);
    }
}
