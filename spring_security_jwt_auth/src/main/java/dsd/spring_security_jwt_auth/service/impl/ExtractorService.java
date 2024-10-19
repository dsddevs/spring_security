package dsd.spring_security_jwt_auth.service.impl;

import dsd.spring_security_jwt_auth.service.IExtractorService;
import dsd.spring_security_jwt_auth.service.IGeneratorService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@AllArgsConstructor
@Service
public class ExtractorService implements IExtractorService {

    private final IGeneratorService generator;

    @Override
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Date extractExpiration(String token) {
        try {
            return extractClaim(token, Claims::getExpiration);
        } catch (Exception e) {
            throw new RuntimeException("ERROR: Extract token expiration is failed: " + e.getMessage());
        }
    }

    @Override
    public String extractJwt(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.substring(7).trim();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(generator.generateSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }
}
