package eu.kamilsikora.financial.api.list.todo.repository;

import eu.kamilsikora.financial.api.list.todo.entity.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoListRepository extends JpaRepository<TodoList, Long> {
}
