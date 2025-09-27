package productivity.todo_service.service;

import productivity.todo_service.dto.TodoDto;
import productivity.todo_service.entity.Todo;
import productivity.todo_service.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {
    private final TodoRepository repository;

    public TodoServiceImpl(TodoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void postTodo(String username, TodoDto dto) {
        repository.save(new Todo(username, dto.title(), dto.description(), dto.finishBy()));
    }

    @Override
    public List<Todo> getAllTodos(String username) {
        return repository.findByUsername(username);
    }
}
