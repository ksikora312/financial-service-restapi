package eu.kamilsikora.financial.api.service.list.todo;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.dto.list.ResponseListCollectionOverview;
import eu.kamilsikora.financial.api.dto.list.ResponseListOverview;
import eu.kamilsikora.financial.api.dto.list.todo.NewToDoListElement;
import eu.kamilsikora.financial.api.dto.list.todo.NewTodoList;
import eu.kamilsikora.financial.api.dto.list.todo.ResponseTodoList;
import eu.kamilsikora.financial.api.dto.list.todo.ResponseTodoListCollection;
import eu.kamilsikora.financial.api.dto.list.todo.ResponseTodoListElement;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoListService {
    private final TodoListRepository todoListRepository;
    private final TodoListElementRepository todoListElementRepository;
    private final ListMapper listMapper;
    private final UserHelperService userHelperService;

    @Transactional
    public ResponseTodoList addNewElement(final UserPrincipal userPrincipal, final NewToDoListElement newToDoListElement) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final TodoList todoList = user.getTodoLists().stream().filter(list -> list.getListId().equals(newToDoListElement.getListId()))
                .findFirst().orElseThrow(() -> new ObjectDoesNotExistException("List does not exist!"));
        final TodoListElement todoListElement = listMapper.mapToEntity(newToDoListElement, todoList);
        todoList.addElement(todoListElement);
        todoListElementRepository.save(todoListElement);
        return listMapper.mapToDto(todoList);
    }

    @Transactional
    public ResponseTodoListElement markElementAs(final UserPrincipal userPrincipal, final Long elementId, final Boolean finished) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final TodoListElement todoListElement = user.markTodoListElementAs(elementId, finished);

        return listMapper.mapToDto(todoListElement);
    }

    @Transactional
    public ResponseTodoList createNewList(final UserPrincipal userPrincipal, final NewTodoList newTodoList) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final TodoList todoList = listMapper.mapToEntity(newTodoList, user);
        user.addNewTodoList(todoList);
        todoListRepository.save(todoList);
        return listMapper.mapToDto(todoList);
    }

    @Transactional
    public ResponseTodoList changeListName(final UserPrincipal userPrincipal, final Long listId, final String listName) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final TodoList list = user.getTodoLists().stream()
                .filter(l -> l.getListId().equals(listId))
                .findFirst().orElseThrow(() -> new ObjectDoesNotExistException("List does not exist!"));

        list.setName(listName);
        return listMapper.mapToDto(list);
    }

    @Transactional
    public ResponseTodoList markAsPrimary(final UserPrincipal userPrincipal, final Long listId) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final TodoList todoList = user.markTodoListAsPrimary(listId);
        return listMapper.mapToDto(todoList);
    }

    @Transactional(readOnly = true)
    public ResponseListCollectionOverview getListsOverview(final UserPrincipal userPrincipal) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final List<TodoList> todoLists = user.getTodoLists();

        final List<ResponseListOverview> overviews = listMapper.mapToOverview(todoLists);
        return new ResponseListCollectionOverview(overviews);
    }

    @Transactional(readOnly = true)
    public ResponseTodoList getPrimaryList(final UserPrincipal userPrincipal) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final TodoList primaryList = user.getTodoLists().stream()
                .filter(TodoList::getIsPrimary)
                .findFirst().orElseThrow(() -> new ObjectDoesNotExistException("List does not exist!"));
        return listMapper.mapToDto(primaryList);
    }

    @Transactional(readOnly = true)
    public ResponseTodoList getListAtId(final UserPrincipal userPrincipal, final Long listId) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final TodoList list = user.getTodoLists().stream()
                .filter(l -> l.getListId().equals(listId))
                .findFirst().orElseThrow(() -> new ObjectDoesNotExistException("List does not exist!"));
        return listMapper.mapToDto(list);
    }

    @Transactional(readOnly = true)
    public ResponseTodoListCollection getTodoLists(final UserPrincipal userPrincipal) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final List<TodoList> userLists = user.getTodoLists();
        final List<ResponseTodoList> responseLists = userLists.stream()
                .map(listMapper::mapToDto)
                .collect(Collectors.toList());
        return new ResponseTodoListCollection(responseLists);
    }

    @Transactional
    public void deleteList(final UserPrincipal userPrincipal, final Long listId) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final TodoList todoList = user.getTodoLists().stream()
                .filter(list -> list.getListId().equals(listId))
                .findFirst().orElseThrow(() -> new ObjectDoesNotExistException("List does not exist!"));
        todoListElementRepository.deleteAll(todoList.getElements());
        todoListRepository.delete(todoList);
        user.getTodoLists().remove(todoList);
    }

    public ResponseTodoList deleteListElement(final UserPrincipal userPrincipal, final Long elementId) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final TodoList todoList = user.getTodoLists().stream().filter(list -> doesListContainElement(list, elementId))
                .findFirst().orElseThrow(() -> new ObjectDoesNotExistException("Elements does not belong to any of user's lists"));
        final TodoListElement todoElement = todoList.getElements().stream()
                .filter(element -> element.getElementId().equals(elementId))
                .findFirst().get();
        todoList.getElements().remove(todoElement);
       todoListElementRepository.delete(todoElement);
       return listMapper.mapToDto(todoList);
    }

    private boolean doesListContainElement(final TodoList todoList, final long elementId) {
        return todoList.getElements().stream()
                .anyMatch(element -> element.getElementId().equals(elementId));
    }


}
