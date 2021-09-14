package eu.kamilsikora.financial.api.list.todo.repository;

import eu.kamilsikora.financial.api.list.todo.entity.TodoListElement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoListElementRepository extends JpaRepository<TodoListElement, Long> {
}
