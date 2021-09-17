package eu.kamilsikora.financial.api.entity.list.todo;

import eu.kamilsikora.financial.api.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.List;

@Entity
@Getter
@Setter
public class TodoList {
    @Id
    @SequenceGenerator(
            name = "todo_list_seq",
            sequenceName = "todo_list_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todo_list_seq")
    private Long listId;
    @OneToMany(mappedBy = "list")
    private List<TodoListElement> elements;
    private Boolean isPrimary;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public TodoListElement addElement(TodoListElement newElement) {
        elements.add(newElement);
        return newElement;
    }

}
