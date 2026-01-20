import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

package com.indexcrm.service.auth;



@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;

    @Value("${app.jwt.secret:}")
    private String jwtSecretPlain;

    @Value("${app.jwt.expiration-ms:3600000}")
    private long jwtExpirationMs;

    private SecretKey jwtSecretKey;

    public AuthService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostConstruct
    private void init() {
        if (jwtSecretPlain == null || jwtSecretPlain.isBlank()) {
            // fallback to a generated key if none provided (not recommended for production)
            this.jwtSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        } else {
            this.jwtSecretKey = Keys.hmacShaKeyFor(jwtSecretPlain.getBytes());
        }
    }

    /**
     * Authenticate credentials and return a signed JWT on success.
     * Throws AuthenticationException if authentication fails.
     */
    public String authenticateAndGetToken(String username, String password) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        return generateToken(auth);
    }

    private String generateToken(Authentication authentication) {
        String username = authentication.getName();
        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(jwtSecretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public List<String> getRolesFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        Object rolesObj = claims.get("roles");
        if (rolesObj == null) return List.of();
        String rolesStr = rolesObj.toString();
        if (rolesStr.isBlank()) return List.of();
        return Arrays.stream(rolesStr.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }
}