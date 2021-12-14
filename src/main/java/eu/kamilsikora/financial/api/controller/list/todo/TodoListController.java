package eu.kamilsikora.financial.api.controller.list.todo;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.dto.list.todo.NewToDoListElement;
import eu.kamilsikora.financial.api.dto.list.todo.NewTodoList;
import eu.kamilsikora.financial.api.dto.list.todo.ResponseTodoList;
import eu.kamilsikora.financial.api.dto.list.todo.ResponseTodoListCollection;
import eu.kamilsikora.financial.api.dto.list.todo.ResponseTodoListCollectionOverview;
import eu.kamilsikora.financial.api.dto.list.todo.ResponseTodoListElement;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/list/todo")
@Api(tags = {"Todo list controller"})
public interface TodoListController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseTodoList createNewList(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody NewTodoList newTodoList);

    @PatchMapping("{id}/{name}")
    @ResponseStatus(HttpStatus.OK)
    ResponseTodoList changeListName(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                    @PathVariable("id") Long listId,
                                    @PathVariable("name") String listName);

    @PostMapping("element")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseTodoList addNewElement(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody NewToDoListElement newToDoListElement);

    @PutMapping("/element/{id}/{state}")
    @ResponseStatus(HttpStatus.OK)
    ResponseTodoListElement markElementAs(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable("id") Long elementId, @PathVariable("state") Boolean done);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseTodoList getPrimaryList(@AuthenticationPrincipal UserPrincipal userPrincipal);

    @GetMapping("/overview")
    @ResponseStatus(HttpStatus.OK)
    ResponseTodoListCollectionOverview getListsOverview(@AuthenticationPrincipal UserPrincipal userPrincipal);

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseTodoList getListAtId(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable("id") Long listId);

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    ResponseTodoListCollection getAllLists(@AuthenticationPrincipal UserPrincipal userPrincipal);

    @PutMapping("/primary/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseTodoList markAsPrimary(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable("id") Long listId);
}
