package productivity.user_service.service;

import java.util.Optional;

public interface TokenService {
    String issue(String username);

    Optional<String> verify(String token);
}
