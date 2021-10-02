package eu.kamilsikora.financial.api.mapper;

import eu.kamilsikora.financial.api.controller.dto.list.todo.NewToDoListElement;
import eu.kamilsikora.financial.api.controller.dto.list.todo.NewTodoList;
import eu.kamilsikora.financial.api.controller.dto.list.todo.ResponseTodoList;
import eu.kamilsikora.financial.api.controller.dto.list.todo.ResponseTodoListElement;
import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.entity.list.todo.TodoList;
import eu.kamilsikora.financial.api.entity.list.todo.TodoListElement;
import eu.kamilsikora.financial.api.entity.list.todo.Priority;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = Priority.class)
public interface ListMapper {
    @Mapping(target = "priority", expression = "java(Priority.of(newTodoListElement.getPriority()))")
    TodoListElement mapToEntity(NewToDoListElement newTodoListElement);

    @Mapping(source = "user", target = "user")
    TodoList mapToEntity(NewTodoList newTodoList, User user);

    @Mapping(target = "dueDate", expression = "java(todoListElement.getDueDate().toString())")
    @Mapping(target = "addedDate", expression = "java(todoListElement.getAddedDate().toString())")
    ResponseTodoListElement mapToDto(TodoListElement todoListElement);

    ResponseTodoList mapToDto(TodoList todoList);
}
