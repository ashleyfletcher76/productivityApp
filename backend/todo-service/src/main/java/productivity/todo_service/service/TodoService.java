package productivity.todo_service.service;

import java.util.List;
import productivity.todo_service.dto.TodoDto;
import productivity.todo_service.entity.Todo;

public interface TodoService {
  void postTodo(String username, TodoDto dto);

  List<Todo> getAllTodos(String username);
}
