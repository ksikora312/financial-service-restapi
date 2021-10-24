package eu.kamilsikora.financial.api.entity;

import eu.kamilsikora.financial.api.entity.expenses.Category;
import eu.kamilsikora.financial.api.entity.expenses.Expenses;
import eu.kamilsikora.financial.api.entity.list.shopping.ShoppingList;
import eu.kamilsikora.financial.api.entity.list.todo.TodoList;
import eu.kamilsikora.financial.api.errorhandling.ObjectDoesNotExistException;
import eu.kamilsikora.financial.api.validation.UniqueUsernameAndEmail;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
@Table(name = "users")
@UniqueUsernameAndEmail
public class User {

    @Id
    @SequenceGenerator(
            name = "user_seq",
            sequenceName = "user_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long userId;
    @NotBlank
    private String username;
    @NotNull
    @Email(message = "Email address must be of correct format!")
    private String email;
    @NotBlank
    private String password;
    private LocalDateTime creationDate;
    private LocalDateTime activationDate;
    private Boolean enabled;
    @OneToMany(mappedBy = "user")
    private List<ShoppingList> shoppingLists;
    @OneToMany(mappedBy = "user")
    private List<TodoList> todoLists;
    @OneToOne(mappedBy = "user")
    private Expenses expenses;
    @OneToMany(mappedBy = "user")
    private List<Category> categories;

    public void addNewTodoList(final TodoList todoList) {
        Optional<TodoList> primaryList = todoLists.stream().filter(TodoList::getIsPrimary).findFirst();
        if (primaryList.isEmpty()) {
            todoList.setIsPrimary(true);
        } else if (todoList.getIsPrimary()) {
            todoLists.stream().filter(TodoList::getIsPrimary).findFirst().ifPresent(list -> list.setIsPrimary(false));
        }
        todoLists.add(todoList);
    }

    public TodoList markTodoListAsPrimary(final Long listId) {
        TodoList todoList = todoLists.stream().filter(list -> list.getListId().equals(listId)).findFirst().orElseThrow(()
                -> new ObjectDoesNotExistException("List does not exist!"));
        todoLists.stream()
                .filter(list -> list.getIsPrimary().equals(true))
                .forEach(list -> list.setIsPrimary(false));
        todoList.setIsPrimary(true);
        return todoList;
    }

    public TodoList markTodoListElementAs(final Long elementId, final Boolean finished) {
        final TodoList todoList = todoLists.stream()
                .filter(list -> doesTodoListContainElement(list, elementId))
                .findAny().orElseThrow(() -> new ObjectDoesNotExistException("Element does not belong to any of user's lists!"));
        todoList.getElements().stream().filter(element -> element.getElementId().equals(elementId))
                .forEach(element -> element.setDone(finished));
        return todoList;
    }

    private boolean doesTodoListContainElement(final TodoList todoList, final Long elementId) {
        return todoList.getElements().stream()
                .anyMatch(element -> element.getElementId().equals(elementId));
    }

    public void addNewShoppingList(final ShoppingList shoppingList) {
        final Optional<ShoppingList> primary = shoppingLists.stream().filter(ShoppingList::getIsPrimary).findFirst();
        if(primary.isEmpty()) {
            shoppingList.setIsPrimary(true);
        }
        else if (shoppingList.getIsPrimary()) {
            shoppingLists.stream().filter(ShoppingList::getIsPrimary).findFirst().ifPresent(list -> list.setIsPrimary(false));
        }
        shoppingLists.add(shoppingList);
    }

    public ShoppingList markShoppingListAsPrimary(final Long id) {
        final ShoppingList shoppingList = shoppingLists.stream()
                .filter(list -> list.getListId().equals(id))
                .findFirst().orElseThrow(() -> new ObjectDoesNotExistException("List does not exist!"));
        shoppingLists.stream()
                .filter(ShoppingList::getIsPrimary)
                .forEach(list -> list.setIsPrimary(false));
        shoppingList.setIsPrimary(true);
        return shoppingList;
    }

    public ShoppingList markShoppingListElementAs(final Long elementId, final Boolean done) {
        final ShoppingList shoppingListContainingTheElement = shoppingLists.stream()
                .filter(list -> doesShoppingListContainElement(list, elementId))
                .findAny().orElseThrow(() -> new ObjectDoesNotExistException("Element does not belong to any of user lists!"));
        shoppingListContainingTheElement.getElements().stream()
                .filter(element -> element.getElementId().equals(elementId))
                .forEach(element -> element.setDone(done));
        final boolean allListElementsDone = shoppingListContainingTheElement.getElements().stream()
                .filter(element -> !element.getDone())
                .findAny().isEmpty();
        shoppingListContainingTheElement.setDone(allListElementsDone);
        return shoppingListContainingTheElement;
    }

    private boolean doesShoppingListContainElement(final ShoppingList list, final Long elementId) {
        return list.getElements().stream()
                .anyMatch(element -> element.getElementId().equals(elementId));
    }

}
