package com.europace.todo_service.service;

import com.europace.todo_service.dto.TodoDto;
import com.europace.todo_service.entity.Todo;

import java.util.List;

public interface TodoService {
    void postTodo(String username, TodoDto dto);

    List<Todo> getAllTodos(String username);

    List<Todo> getTodos(String username);
}
