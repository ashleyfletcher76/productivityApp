package productivity.todo_service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.List;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    private final RestClient userServiceClient;

    private static final List<SimpleGrantedAuthority> USER_ROLE =
            List.of(new SimpleGrantedAuthority("ROLE_USER"));

    public AuthTokenFilter(RestClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return "/todos/health".equals(request.getRequestURI());
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String token = extractBearerOr401(request);
        if (token == null) {
            send401(response);
            return ;
        }

        TokenVerifyResponse verifyResponse;
        try {
            verifyResponse = callUserService(token);
        } catch (RestClientResponseException e) {
            send401(response);
            return ;
        } catch (RestClientException e) {
            send503(response);
            return ;
        }
        if (verifyResponse == null || !verifyResponse.valid()) {
            send401(response);
            return ;
        }

        setAuthentication(verifyResponse.username());
        filterChain.doFilter(request, response);
    }

    private String extractBearerOr401(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            return null;
        }
        return auth.substring("Bearer ".length());
    }

    private TokenVerifyResponse callUserService(String token) {
        return userServiceClient.post()
                .uri("/token")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new TokenRequest(token))
                .retrieve()
                .body(TokenVerifyResponse.class);
    }

    private void setAuthentication(String username) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(username, null, USER_ROLE);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void send401(HttpServletResponse response) throws IOException {
        SecurityContextHolder.clearContext();
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private void send503(HttpServletResponse response) throws IOException {
        SecurityContextHolder.clearContext();
        response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
    }

    private record TokenRequest(String token) {}

    private record TokenVerifyResponse(boolean valid, String username) {}
}
