package productivity.auth_service.service;

import org.springframework.security.core.Authentication;

public interface TokenService {
    String generateToken(Authentication auth);
}
