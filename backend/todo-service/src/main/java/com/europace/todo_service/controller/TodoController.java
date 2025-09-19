package com.europace.todo_service.controller;

import com.europace.todo_service.config.AuthClientConfig;
import com.europace.todo_service.dto.TodoDto;
import com.europace.todo_service.entity.Todo;
import com.europace.todo_service.service.TodoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService service;
    private final RestClient restClient;

    public TodoController(TodoService service, RestClient restClient) {
        this.service = service;
        this.restClient = restClient;
    }

    @GetMapping("/health")
    public String health() { return "Ok"; }

    @PostMapping()
    public ResponseEntity<String> postTodo(HttpServletRequest request, @RequestBody @Valid TodoDto dto) {
        String username = requireAuth(request);
        service.postTodo(username, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Todo has been posted!");
    }

    @GetMapping()
    public ResponseEntity<List<Todo>> getAllTodos(HttpServletRequest request) {
        String username = requireAuth(request);
        List<Todo> todos = service.getAllTodos(username);
        return ResponseEntity.ok(todos);
    }

    private String requireAuth(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer "))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing Bearer token");

        String token = auth.substring("Bearer ".length());

        try {
            TokenVerifyResponse verifyResponse = restClient.post()
                    .uri("/token")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new TokenRequest(token))
                    .retrieve()
                    .body(TokenVerifyResponse.class);

            if (verifyResponse == null || !verifyResponse.valid())
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
            return verifyResponse.username();

        } catch(HttpClientErrorException.Unauthorized e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        } catch(RestClientException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Auth service unavailable");
        }

    }

    private record TokenRequest(String token) {}

    private record TokenVerifyResponse(boolean valid, String username) {}
}

