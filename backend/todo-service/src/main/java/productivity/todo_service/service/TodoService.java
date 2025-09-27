package productivity.todo_service.service;

import productivity.todo_service.dto.TodoDto;
import productivity.todo_service.entity.Todo;

import java.util.List;

public interface TodoService {
    void postTodo(String username, TodoDto dto);

    List<Todo> getAllTodos(String username);
}
