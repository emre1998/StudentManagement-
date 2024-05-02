package com.App.StudentManagement.Security;

import com.App.StudentManagement.Model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY = "roles";

    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;

    public String generateToken(User user) {
        String username = user.getUsername();

        Claims claims = Jwts.claims().setSubject(username);

        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getExpirationTime());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    public User getAuthentication(String authToken) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(authToken).getBody();
            String username = claims.getSubject();
            // Kullanıcı bilgilerini almak için gerekli işlemleri yapın (örneğin, UserRepository ile kullanıcıyı bulun)
            // Daha sonra kullanıcıyı döndürün
            return null;
        } catch (JwtException e) {
            log.error("JWT token parse error: {}", e.getMessage());
            return null;
        }
    }
}
