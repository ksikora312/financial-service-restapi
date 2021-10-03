package eu.kamilsikora.financial.api.entity;

import eu.kamilsikora.financial.api.entity.list.todo.TodoList;
import eu.kamilsikora.financial.api.validation.UniqueUsernameAndEmail;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    private List<TodoList> todoLists;

    public void activateAccount() {
        this.enabled = true;
        this.activationDate = LocalDateTime.now();
    }

    public void addNewList(final TodoList todoList) {
        Optional<TodoList> primaryList = todoLists.stream().filter(TodoList::getIsPrimary).findFirst();
        if(primaryList.isEmpty()) {
            todoList.setIsPrimary(true);
        }
        else if(todoList.getIsPrimary()) {
            todoLists.stream().filter(TodoList::getIsPrimary).findFirst().ifPresent(list -> list.setIsPrimary(false));
        }
        todoLists.add(todoList);
    }

}
