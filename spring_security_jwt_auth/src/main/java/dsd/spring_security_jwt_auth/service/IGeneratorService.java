package dsd.spring_security_jwt_auth.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;

public interface IGeneratorService {
    String generateToken(UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);

    Key generateSignInKey();
}
