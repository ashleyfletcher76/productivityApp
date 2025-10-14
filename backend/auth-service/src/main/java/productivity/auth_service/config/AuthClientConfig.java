package productivity.auth_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AuthClientConfig {
  @Bean
  RestClient userServiceClient(
      @Value("${user.service.base-url:http://localhost:8081}") String baseUrl) {
    return RestClient.builder().baseUrl(baseUrl).build();
  }
}
