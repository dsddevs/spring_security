package dsd.spring_security_jwt_auth.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import dsd.spring_security_jwt_auth.dto.AuthResponseDto;
import dsd.spring_security_jwt_auth.model.entity.UserEntity;
import dsd.spring_security_jwt_auth.service.IExtractorService;
import dsd.spring_security_jwt_auth.service.IGeneratorService;
import dsd.spring_security_jwt_auth.service.IJwtManagerService;
import dsd.spring_security_jwt_auth.service.IValidatorService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@AllArgsConstructor
@Service
public class ValidatorService implements IValidatorService {

    private final IExtractorService extractor;
    private final IGeneratorService generator;
    private final IJwtManagerService jwtManager;

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = extractor.extractEmail(token);
        boolean isTokenExpired = extractor.extractExpiration(token).before(new Date());
        return (email.equals(userDetails.getUsername())) && !isTokenExpired;
    }

    @Override
    public void processValidToken(UserEntity user, String refreshToken, HttpServletResponse response) throws IOException {
        if (isTokenValid(refreshToken, user)) {
            String accessToken = generator.generateToken(user);
            jwtManager.revokeAllUserTokens(user);
            jwtManager.saveUserToken(user, accessToken);
            AuthResponseDto authResponse = getAuthResponse(accessToken, refreshToken);
            new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
        } else {
            throw new RuntimeException("ERROR: JWT is not valid - refresh token method");
        }
    }

    private AuthResponseDto getAuthResponse(String accessToken, String refreshToken) {
        try {
            return AuthResponseDto
                    .builder()
                    .success(true)
                    .message("SUCCESS: JWT refreshed")
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (Exception e) {
            return AuthResponseDto
                    .builder()
                    .success(false)
                    .message("ERROR: JWT refreshing is failed: " + e.getMessage())
                    .build();
        }
    }

}
