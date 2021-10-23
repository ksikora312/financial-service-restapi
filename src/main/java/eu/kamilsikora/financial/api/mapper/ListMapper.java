package eu.kamilsikora.financial.api.mapper;

import eu.kamilsikora.financial.api.dto.list.shopping.NewShoppingListDto;
import eu.kamilsikora.financial.api.dto.list.shopping.ResponseShoppingListDto;
import eu.kamilsikora.financial.api.dto.list.shopping.ResponseShoppingListElementDto;
import eu.kamilsikora.financial.api.dto.list.todo.NewToDoListElement;
import eu.kamilsikora.financial.api.dto.list.todo.NewTodoList;
import eu.kamilsikora.financial.api.dto.list.todo.ResponseTodoList;
import eu.kamilsikora.financial.api.dto.list.todo.ResponseTodoListElement;
import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.entity.list.shopping.ShoppingList;
import eu.kamilsikora.financial.api.entity.list.shopping.ShoppingListElement;
import eu.kamilsikora.financial.api.entity.list.todo.TodoList;
import eu.kamilsikora.financial.api.entity.list.todo.TodoListElement;
import eu.kamilsikora.financial.api.entity.list.todo.Priority;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = Priority.class)
public abstract class ListMapper {

    @AfterMapping
    protected void fillRestOfData(@MappingTarget TodoListElement todoListElement) {
        todoListElement.setAddedDate(LocalDateTime.now());
        todoListElement.setDone(false);
    }

    @AfterMapping
    protected void fillRestOfData(@MappingTarget TodoList todoList) {
        if(todoList.getIsPrimary() == null) {
            todoList.setIsPrimary(false);
        }
    }

    @AfterMapping
    protected void fillRestOfData(@MappingTarget ShoppingList shoppingList) {
        shoppingList.setDone(false);
        shoppingList.setCreatedDate(LocalDateTime.now());
        if(shoppingList.getIsPrimary() == null) {
            shoppingList.setIsPrimary(false);
        }
    }

    @Mapping(target = "priority", expression = "java(Priority.of(newTodoListElement.getPriority()))")
    @Mapping(source = "newTodoListElement.name", target = "name")
    @Mapping(source = "todoList", target = "list")
    public abstract TodoListElement mapToEntity(NewToDoListElement newTodoListElement, TodoList todoList);

    @Mapping(source = "user", target = "user")
    public abstract TodoList mapToEntity(NewTodoList newTodoList, User user);

    public abstract ResponseTodoListElement mapToDto(TodoListElement todoListElement);

    public abstract ResponseTodoList mapToDto(TodoList todoList);

    @Mapping(target = "user", source = "user")
    public abstract ShoppingList mapToEntity(NewShoppingListDto newShoppingList, User user);

    @Mapping(target = "category", expression = "java(shoppingListElement.getCategory() != null? shoppingListElement.getCategory().getName(): null)")
    public abstract ResponseShoppingListElementDto mapToDto(ShoppingListElement shoppingListElement);

    public abstract ResponseShoppingListDto mapToDto(ShoppingList shoppingList);
}
