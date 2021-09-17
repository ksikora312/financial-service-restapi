package eu.kamilsikora.financial.api.repository.list;

import eu.kamilsikora.financial.api.entity.list.todo.TodoListElement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoListElementRepository extends JpaRepository<TodoListElement, Long> {
}
