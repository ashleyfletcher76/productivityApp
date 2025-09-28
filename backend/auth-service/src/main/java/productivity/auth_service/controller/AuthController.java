package productivity.auth_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import productivity.auth_service.service.TokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/health")
    public String health() { return "Ok"; }

    @GetMapping("/")
    public String checkName(Authentication auth) {
        return "Hello, " + auth.getName();
    }


    @PostMapping("/token")
    public String token(Authentication auth) {
        LOGGER.debug("Token request for user: '{}'", auth.getName());
        String token = tokenService.generateToken(auth);
        LOGGER.debug("Token granted {}", token);
        return token;
    }
}
