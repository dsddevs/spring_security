package dsd.jwt.service.generation;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtGeneratorInService implements JwtGeneratorService {

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private long jwtExpiration;
    @Value("${jwt.refresh-expiration}")
    private long jwtRefreshExpiration;

    @Override
    public String generateAccessJwt(UserDetails userDetails) {
        return buildJwts(new HashMap<>(), userDetails, jwtExpiration);
    }

    @Override
    public String generateRefreshJwt(UserDetails userDetails) {
        return buildJwts(new HashMap<>(), userDetails, jwtRefreshExpiration);
    }

    @Override
    public Key generateSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String buildJwts(Map<String, Object> claims, UserDetails userDetails, long jwtExpiration) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(generateSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}

