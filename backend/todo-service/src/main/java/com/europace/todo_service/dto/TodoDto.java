package com.europace.todo_service.dto;

import java.time.LocalDate;

public record TodoDto(
        String title,
        String description,
        LocalDate finishBy
) {}
