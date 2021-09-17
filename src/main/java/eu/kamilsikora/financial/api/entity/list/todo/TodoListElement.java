package eu.kamilsikora.financial.api.entity.list.todo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
public class TodoListElement {

    @Id
    @SequenceGenerator(
            name = "todo_list_elem_seq",
            sequenceName = "todo_list_elem_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todo_list_elem_seq")
    private Long elementId;
    private String name;
    private String description;
    private LocalDateTime addedDate;
    private LocalDateTime dueDate;
    @Convert(converter = PriorityConverter.class)
    private Priority priority;
    private Boolean done;

    @ManyToOne
    @JoinColumn(name = "list_id")
    private TodoList list;
}
