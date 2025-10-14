package productivity.todo_service.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import productivity.todo_service.dto.TodoDto;
import productivity.todo_service.entity.Todo;
import productivity.todo_service.service.TodoService;

@RestController
@RequestMapping("/todos")
public class TodoController {

  private final TodoService service;

  private static final Logger LOG = LoggerFactory.getLogger(TodoController.class);

  public TodoController(TodoService service) {
    this.service = service;
  }

  @GetMapping("/health")
  public String health() {
    return "Ok";
  }

  @PostMapping()
  public ResponseEntity<String> postTodo(
      Authentication authentication, @RequestBody @Valid TodoDto dto) {
    String username = authentication.getName();
    service.postTodo(username, dto);
    return ResponseEntity.status(HttpStatus.CREATED).body("Todo has been posted!");
  }

  @GetMapping()
  public ResponseEntity<List<Todo>> getAllTodos(Authentication authentication) {
    String username = authentication.getName();
    String roles = authentication.getDetails().toString();
    LOG.info("Roles for user: {}", roles);
    List<Todo> todos = service.getAllTodos(username);
    return ResponseEntity.ok(todos);
  }

  @GetMapping("/search")
  public ResponseEntity<List<Todo>> getTodos(
      Authentication authentication, @RequestParam String name) {
    if (!name.equals(authentication.getName()))
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    List<Todo> todos = service.getAllTodos(name);
    return ResponseEntity.ok(todos);
  }
}
