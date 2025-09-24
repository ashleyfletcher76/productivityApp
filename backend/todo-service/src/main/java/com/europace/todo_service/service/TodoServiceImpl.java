package com.europace.todo_service.service;

import com.europace.todo_service.dto.TodoDto;
import com.europace.todo_service.entity.Todo;
import com.europace.todo_service.repository.TodoRepository;
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

    @Override
    public List<Todo> getTodos(String username) {
        return repository.findByUsername(username);
    }
}
