package productivity.user_service.dto;

import java.util.List;

public record UserResponse(String username, List<String> roles) {}
