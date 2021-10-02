package eu.kamilsikora.financial.api.repository.list.todo;

import eu.kamilsikora.financial.api.entity.list.todo.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {
    @Query("SELECT l FROM todo_list l WHERE l.user.userId = ?1")
    List<TodoList> findByUserId(Long userId);
}
