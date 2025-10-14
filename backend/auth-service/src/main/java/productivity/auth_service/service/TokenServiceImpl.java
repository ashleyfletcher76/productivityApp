package productivity.auth_service.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import productivity.auth_service.controller.AuthController.UserServiceResponse;

@Service
public class TokenServiceImpl implements TokenService {

  private final JwtEncoder jwtEncoder;
  private final String issuer;

  public TokenServiceImpl(
      JwtEncoder jwtEncoder,
      @Value("${spring.security.oauth2.authorizationserver.issuer}") String issuer) {
    this.jwtEncoder = jwtEncoder;
    this.issuer = issuer;
  }

  @Override
  public String generateToken(UserServiceResponse userServiceResponse) {
    Instant now = Instant.now();

    JwtClaimsSet claimsSet =
        JwtClaimsSet.builder()
            .issuer(issuer)
            .issuedAt(now)
            .expiresAt(now.plus(1, ChronoUnit.HOURS))
            .subject(userServiceResponse.username())
            .claim("role", String.join(" ", userServiceResponse.roles()))
            .build();
    return this.jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
  }
}
