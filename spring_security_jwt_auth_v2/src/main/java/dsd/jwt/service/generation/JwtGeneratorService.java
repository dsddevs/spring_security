package dsd.jwt.service.generation;

import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;

public interface JwtGeneratorService {
    String generateAccessJwt(UserDetails userDetails);

    String generateRefreshJwt(UserDetails userDetails);

    Key generateSignInKey();

}
