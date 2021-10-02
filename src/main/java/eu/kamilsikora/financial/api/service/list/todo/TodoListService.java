package eu.kamilsikora.financial.api.service.list.todo;

import eu.kamilsikora.financial.api.mapper.ListMapper;
import eu.kamilsikora.financial.api.repository.list.todo.TodoListElementRepository;
import eu.kamilsikora.financial.api.repository.list.todo.TodoListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoListService {
    private final TodoListRepository todoListRepository;
    private final TodoListElementRepository todoListElementRepository;
    private ListMapper listMapper;

//    public

}
