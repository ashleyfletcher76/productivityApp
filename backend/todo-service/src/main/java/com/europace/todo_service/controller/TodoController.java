package com.europace.todo_service.controller;

import com.europace.todo_service.dto.TodoDto;
import com.europace.todo_service.entity.Todo;
import com.europace.todo_service.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping("/health")
    public String health() { return "Ok"; }

    @PostMapping()
    public ResponseEntity<String> postTodo(@Valid @RequestBody TodoDto dto) {
        service.postTodo(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Todo has been posted!");
    }

    @GetMapping()
    public ResponseEntity<List<Todo>> getAllTodos() {
        List<Todo> todos = service.getAllTodos();
        return ResponseEntity.ok(todos);
    }
}
