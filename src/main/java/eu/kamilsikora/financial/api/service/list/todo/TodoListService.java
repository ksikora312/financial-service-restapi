package eu.kamilsikora.financial.api.service.list.todo;

import eu.kamilsikora.financial.api.controller.dto.list.todo.NewToDoListElement;
import eu.kamilsikora.financial.api.controller.dto.list.todo.ResponseTodoList;
import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.entity.list.todo.TodoList;
import eu.kamilsikora.financial.api.entity.list.todo.TodoListElement;
import eu.kamilsikora.financial.api.errorhandling.ObjectDoesNotExistException;
import eu.kamilsikora.financial.api.mapper.ListMapper;
import eu.kamilsikora.financial.api.repository.UserRepository;
import eu.kamilsikora.financial.api.repository.list.todo.TodoListElementRepository;
import eu.kamilsikora.financial.api.repository.list.todo.TodoListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoListService {
    private final TodoListRepository todoListRepository;
    private final TodoListElementRepository todoListElementRepository;
    private final ListMapper listMapper;
    private final UserRepository userRepository;

    public ResponseTodoList addNewElement(Long userId, NewToDoListElement newToDoListElement) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new ObjectDoesNotExistException("User does not exist!"));
        TodoList todoList = user.getTodoLists().stream().filter(list -> list.getListId() == newToDoListElement.getListId())
                .findFirst().orElseThrow(() -> new ObjectDoesNotExistException("List does not exist!"));
        TodoListElement todoListElement = listMapper.mapToEntity(newToDoListElement, todoList);
        todoList.addElement(todoListElement);
        todoListElementRepository.save(todoListElement);
        return listMapper.mapToDto(todoList);
    }
}
