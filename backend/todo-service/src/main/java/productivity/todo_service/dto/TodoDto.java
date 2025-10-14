package productivity.todo_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record TodoDto(
    @NotBlank @Size(max = 120) String title,
    @NotBlank @Size(max = 120) String description,
    LocalDate finishBy) {}
