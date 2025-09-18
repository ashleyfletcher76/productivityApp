package com.europace.todo_service.service;

import com.europace.todo_service.dto.TodoDto;
import com.europace.todo_service.entity.Todo;

import java.util.List;

public interface TodoService {
    void postTodo(TodoDto dto);

    List<Todo> getAllTodos();
}
