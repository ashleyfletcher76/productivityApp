package productivity.auth_service.controller;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import productivity.auth_service.config.RsaKeyProperties;

import java.util.Map;

@RestController
public class JwksController {
    private final RsaKeyProperties keys;
    private final String kid = "v1"; // move to config

    public JwksController(RsaKeyProperties keys) {
        this.keys = keys;
    }

    @GetMapping(value = "/.well-known/jwks.json", produces = "application/json")
    public Map<String, Object> jwks() {
        RSAKey jwk = new RSAKey.Builder(keys.publicKey())
                .keyID(kid)
                .algorithm(JWSAlgorithm.RS256)
                .build();

        return new JWKSet(jwk).toJSONObject();
    }
}
