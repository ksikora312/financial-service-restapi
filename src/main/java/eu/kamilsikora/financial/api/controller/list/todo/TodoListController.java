package eu.kamilsikora.financial.api.controller.list.todo;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.controller.dto.list.todo.NewToDoListElement;
import eu.kamilsikora.financial.api.controller.dto.list.todo.NewTodoList;
import eu.kamilsikora.financial.api.controller.dto.list.todo.ResponseTodoList;
import eu.kamilsikora.financial.api.controller.dto.list.todo.ResponseTodoListCollection;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/list/todo")
public interface TodoListController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseTodoList createNewList(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody NewTodoList newTodoList);

    @PostMapping("element")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseTodoList addNewElement(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody NewToDoListElement newToDoListElement);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseTodoList getPrimaryList(@AuthenticationPrincipal UserPrincipal userPrincipal);

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    ResponseTodoListCollection getAllLists(@AuthenticationPrincipal UserPrincipal userPrincipal);
}
