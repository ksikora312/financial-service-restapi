package eu.kamilsikora.financial.api.mapper;

import eu.kamilsikora.financial.api.controller.dto.list.todo.NewToDoListElement;
import eu.kamilsikora.financial.api.entity.list.todo.TodoList;
import eu.kamilsikora.financial.api.entity.list.todo.TodoListElement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ListMapper {
    TodoListElement mapToEntity(NewToDoListElement newToDoListElement);
    TodoList mapToEntity(TodoList todoList);
}
