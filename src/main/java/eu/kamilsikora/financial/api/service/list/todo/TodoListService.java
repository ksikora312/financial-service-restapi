package eu.kamilsikora.financial.api.service.list.todo;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.controller.dto.list.todo.NewToDoListElement;
import eu.kamilsikora.financial.api.controller.dto.list.todo.NewTodoList;
import eu.kamilsikora.financial.api.controller.dto.list.todo.ResponseTodoList;
import eu.kamilsikora.financial.api.controller.dto.list.todo.ResponseTodoListCollection;
import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.entity.list.todo.TodoList;
import eu.kamilsikora.financial.api.entity.list.todo.TodoListElement;
import eu.kamilsikora.financial.api.errorhandling.ObjectDoesNotExistException;
import eu.kamilsikora.financial.api.mapper.ListMapper;
import eu.kamilsikora.financial.api.repository.list.todo.TodoListElementRepository;
import eu.kamilsikora.financial.api.repository.list.todo.TodoListRepository;
import eu.kamilsikora.financial.api.service.UserHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoListService {
    private final TodoListRepository todoListRepository;
    private final TodoListElementRepository todoListElementRepository;
    private final ListMapper listMapper;
    private final UserHelperService userHelperService;

    public ResponseTodoList addNewElement(final UserPrincipal userPrincipal, final NewToDoListElement newToDoListElement) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final TodoList todoList = user.getTodoLists().stream().filter(list -> list.getListId().equals(newToDoListElement.getListId()))
                .findFirst().orElseThrow(() -> new ObjectDoesNotExistException("List does not exist!"));
        final TodoListElement todoListElement = listMapper.mapToEntity(newToDoListElement, todoList);
        todoList.addElement(todoListElement);
        todoListElementRepository.save(todoListElement);
        return listMapper.mapToDto(todoList);
    }

    public ResponseTodoList createNewList(final UserPrincipal userPrincipal, final NewTodoList newTodoList) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final TodoList todoList = listMapper.mapToEntity(newTodoList, user);
        user.addNewList(todoList);
        todoListRepository.save(todoList);
        return listMapper.mapToDto(todoList);
    }

    public ResponseTodoList getPrimaryList(final UserPrincipal userPrincipal) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final TodoList primaryList = user.getTodoLists().stream()
                .filter(TodoList::getIsPrimary)
                .findFirst().orElseThrow(() -> new ObjectDoesNotExistException("List does not exist!"));
        return listMapper.mapToDto(primaryList);
    }

    public ResponseTodoListCollection getTodoLists(final UserPrincipal userPrincipal) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final List<TodoList> userLists = user.getTodoLists();
        final List<ResponseTodoList> responseLists = userLists.stream()
                .map(listMapper::mapToDto)
                .collect(Collectors.toList());
        return new ResponseTodoListCollection(responseLists);
    }

}
