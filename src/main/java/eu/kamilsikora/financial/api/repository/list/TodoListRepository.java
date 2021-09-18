package eu.kamilsikora.financial.api.repository.list;

import eu.kamilsikora.financial.api.entity.list.todo.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {
}
