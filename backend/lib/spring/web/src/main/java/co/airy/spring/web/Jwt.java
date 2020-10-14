package co.airy.spring.web;

import co.airy.log.AiryLoggerFactory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.Charset;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Component
public class Jwt {
    private static final Logger log = AiryLoggerFactory.getLogger(Jwt.class);

    private final Key signingKey;

    public static final String USER_ID_CLAIM = "user_id";

    public Jwt(@Value("${auth.jwt-secret}") String tokenKey) {
        this.signingKey = parseSigningKey(tokenKey);
    }

    public String tokenFor(String userId) {
        Date now = Date.from(Instant.now());

        Map<String, Object> claims = new HashMap<>();
        claims.put(USER_ID_CLAIM, userId);

        JwtBuilder builder = Jwts.builder()
                .setId(userId)
                .setSubject(userId)
                .setIssuedAt(now)
                .addClaims(claims)
                .signWith(signingKey, SignatureAlgorithm.HS256);

        Date exp = Date.from(Instant.now().plus(Duration.ofHours(1)));
        builder.setExpiration(exp);

        return builder.compact();
    }

    public String authenticate(final String authHeader) throws HttpClientErrorException.Unauthorized {
        Claims claims = null;
        if (authHeader != null) {
            try {
                claims = extractClaims(authHeader);
            } catch (Exception e) {
                log.error("Failed to extract claims from token: " + e.getMessage());
            }
        }

        if (claims == null) {
            throw new HttpClientErrorException(UNAUTHORIZED, "Unauthorized", null, null, Charset.defaultCharset());
        }

        String userId;
        try {
            userId = (String) claims.get(USER_ID_CLAIM);
            return userId;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new HttpClientErrorException(UNAUTHORIZED, "Unauthorized", null, null, Charset.defaultCharset());
        }
    }

    private Key parseSigningKey(String tokenKey) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(tokenKey);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    private Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
    }
}
