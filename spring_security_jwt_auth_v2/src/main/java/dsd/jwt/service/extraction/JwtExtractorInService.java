package dsd.jwt.service.extraction;

import dsd.jwt.service.generation.JwtGeneratorService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class JwtExtractorInService implements JwtExtractorService {

    private final JwtGeneratorService jwtGenerator;

    @Override
    public String extractEmailByJwt(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    @Override
    public Date extractExpirationByJwt(String jwt) {
        return extractClaim(jwt, Claims::getExpiration);
    }

    @Override
    public String extractJwtByRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        boolean isJwtExists = authHeader != null && authHeader.startsWith("Bearer ");
        return isJwtExists ? authHeader.substring(7).trim() : null;
    }

    private <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(jwtGenerator.generateSignInKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        return claimsResolver.apply(claims);
    }
}
