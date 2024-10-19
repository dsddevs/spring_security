package dsd.spring_security_jwt_auth.service;

import dsd.spring_security_jwt_auth.model.entity.UserEntity;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

public interface IValidatorService {
    boolean isTokenValid(String token, UserDetails userDetails);

    void processValidToken(UserEntity user, final String refreshToken, HttpServletResponse response) throws IOException;
}
