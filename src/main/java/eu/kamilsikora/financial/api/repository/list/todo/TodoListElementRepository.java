package eu.kamilsikora.financial.api.repository.list.todo;

import eu.kamilsikora.financial.api.entity.list.todo.TodoListElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoListElementRepository extends JpaRepository<TodoListElement, Long> {
}
