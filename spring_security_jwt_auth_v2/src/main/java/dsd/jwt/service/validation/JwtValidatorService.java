package dsd.jwt.service.validation;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtValidatorService {
    boolean isJwtValid(String jwt, UserDetails userDetails);
}
