package productivity.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;

public record UserRequest(
    @NotBlank @Size(min = 3, max = 10) String username,
    @NotBlank
        @Size(min = 5, max = 20)
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$")
        String password,
    List<String> roles) {}
