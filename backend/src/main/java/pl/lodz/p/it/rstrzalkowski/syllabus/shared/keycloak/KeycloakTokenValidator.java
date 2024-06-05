package pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.security.interfaces.RSAPublicKey;
import java.util.Calendar;

@Component
public class KeycloakTokenValidator {

    @Value("${keycloak.external.jwks.uri}")
    private String jwksUri;

    public boolean validateToken(DecodedJWT jwt) {
        if (jwt.getExpiresAt().before(Calendar.getInstance().getTime())) {
            return false;
        }

        try {
            JwkProvider provider = new MyJwkProvider(jwksUri);
            Jwk jwk = provider.get(jwt.getKeyId());

            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
            algorithm.verify(jwt);
        } catch (WebClientResponseException | JwkException wcre) {
            return false;
        }

        return true;
    }
}
