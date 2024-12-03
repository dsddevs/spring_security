package dsd.jwt.service.validation;

import dsd.jwt.model.entity.JwtEntity;
import dsd.jwt.model.repository.JwtRepository;
import dsd.jwt.service.extraction.JwtExtractorService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtValidatorInService implements JwtValidatorService {

    private final JwtExtractorService jwtExtractorService;
    private final JwtRepository jwtRepository;

    @Override
    public boolean isJwtValid(String jwt, UserDetails userDetails) {
        try {
            return isEmailMatched(jwt, userDetails) && !isJwtExpired(jwt) && !isJwtRevoked(jwt);
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT: {}", e.getMessage());
            return false;
        }
    }

    private boolean isEmailMatched(String jwt, UserDetails userDetails) {
        final String email = jwtExtractorService.extractEmailByJwt(jwt);
        final boolean isEmailMatched = email.equals(userDetails.getUsername());
        if (!isEmailMatched) {
            log.error("JWT doesn't match the user");
            return false;
        }
        return true;
    }

    private boolean isJwtExpired(String jwt) {
        Date expirationDate = jwtExtractorService.extractExpirationByJwt(jwt);
        final boolean isJwtExpired = expirationDate.before(new Date());
        if (isJwtExpired) {
            log.error("JWT expired");
            return true;
        }
        return false;
    }

    private boolean isJwtRevoked(String jwt) {
        Optional<JwtEntity> jwtEntity = jwtRepository.findByJwt(jwt);
        boolean isRevoked = jwtEntity.map(JwtEntity::isRevoked).orElse(true);
        if (isRevoked) {
            log.error("JWT revoked");
            return true;
        }
        return false;
    }
}

