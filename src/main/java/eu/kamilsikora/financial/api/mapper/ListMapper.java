package eu.kamilsikora.financial.api.mapper;

import eu.kamilsikora.financial.api.dto.list.ResponseListOverview;
import eu.kamilsikora.financial.api.dto.list.shopping.NewShoppingListDto;
import eu.kamilsikora.financial.api.dto.list.shopping.NewShoppingListElementDto;
import eu.kamilsikora.financial.api.dto.list.shopping.ResponseShoppingListDto;
import eu.kamilsikora.financial.api.dto.list.shopping.ResponseShoppingListElementDto;
import eu.kamilsikora.financial.api.dto.list.shopping.UpdateShoppingListDto;
import eu.kamilsikora.financial.api.dto.list.shopping.UpdateShoppingListElementDto;
import eu.kamilsikora.financial.api.dto.list.todo.NewToDoListElement;
import eu.kamilsikora.financial.api.dto.list.todo.NewTodoList;
import eu.kamilsikora.financial.api.dto.list.todo.ResponseTodoList;
import eu.kamilsikora.financial.api.dto.list.todo.ResponseTodoListElement;
import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.entity.list.shopping.ShoppingList;
import eu.kamilsikora.financial.api.entity.list.shopping.ShoppingListElement;
import eu.kamilsikora.financial.api.entity.list.todo.Priority;
import eu.kamilsikora.financial.api.entity.list.todo.TodoList;
import eu.kamilsikora.financial.api.entity.list.todo.TodoListElement;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", imports = {Priority.class, LocalDateTime.class})
public abstract class ListMapper {

    @AfterMapping
    protected void fillRestOfData(@MappingTarget TodoListElement todoListElement) {
        todoListElement.setAddedDate(LocalDateTime.now());
        todoListElement.setDone(false);
    }

    @AfterMapping
    protected void fillRestOfData(@MappingTarget TodoList todoList) {
        if (todoList.getIsPrimary() == null) {
            todoList.setIsPrimary(false);
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

    @Mapping(target = "list", source = "shoppingList")
    @Mapping(target = "value", source = "newElement.value")
    @Mapping(target = "name", source = "newElement.name")
    @Mapping(target = "done", expression = "java(false)")
    @Mapping(target = "addedDate", expression = "java(LocalDateTime.now())")
    public abstract ShoppingListElement mapToEntity(NewShoppingListElementDto newElement, ShoppingList shoppingList);

    @Mapping(target = "user", source = "user")
    @Mapping(target = "name", source = "newShoppingList.name")
    @Mapping(target = "done", expression = "java(false)")
    @Mapping(target = "createdDate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "isPrimary", expression = "java(newShoppingList.getIsPrimary() == null? false: newShoppingList.getIsPrimary())")
    @Mapping(target = "active", expression = "java(true)")
    public abstract ShoppingList mapToEntity(NewShoppingListDto newShoppingList, User user);

    public abstract ResponseShoppingListElementDto mapToDto(ShoppingListElement shoppingListElement);

    public abstract ResponseShoppingListDto mapToDto(ShoppingList shoppingList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "value", source = "update.value")
    @Mapping(target = "elementId", ignore = true)
    @Mapping(target = "name", ignore = true)
    public abstract void mapIntoEntity(@MappingTarget ShoppingListElement shoppingListElement,
                                       UpdateShoppingListElementDto update);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "listId", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "value", source = "update.value")
    @Mapping(target = "user", ignore = true)
    public abstract void mapIntoEntity(@MappingTarget ShoppingList shoppingList, UpdateShoppingListDto update);

    @Mapping(target = "listId", source = "todoList.listId")
    public abstract ResponseListOverview mapToOverview(TodoList todoList);

    public abstract List<ResponseListOverview> mapToOverview(List<TodoList> todoLists);

    @Mapping(target = "listId", source = "list.listId")
    public abstract ResponseListOverview mapToOverview(ShoppingList list);

    public abstract List<ResponseListOverview> mapShoppingListsToOverview(List<ShoppingList> shoppingLists);
}
