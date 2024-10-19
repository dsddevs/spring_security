package dsd.spring_security_jwt_auth.service.impl;

import dsd.spring_security_jwt_auth.model.entity.UserEntity;
import dsd.spring_security_jwt_auth.model.repo.UserRepo;
import dsd.spring_security_jwt_auth.service.IExtractorService;
import dsd.spring_security_jwt_auth.service.IValidatorService;
import dsd.spring_security_jwt_auth.service.IRefresherToken;
import dsd.spring_security_jwt_auth.utils.IUserChecker;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@AllArgsConstructor
@Service
public class RefresherTokenService implements IRefresherToken, IUserChecker {

    private final IExtractorService extractor;
    private final IValidatorService jwtValidator;
    private final UserRepo userRepo;

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String refreshToken = extractor.extractJwt(request);
        if (refreshToken == null) {
            throw new RuntimeException("ERROR: Refresh token is missing");
        }

        final String email = extractor.extractEmail(refreshToken);

        if (email != null) {
            UserEntity user = checkUserExisting(email, userRepo);
            if (user == null) {
                throw new RuntimeException("ERROR: User not found");
            }
            jwtValidator.processValidToken(user, refreshToken, response);
        } else {
            throw new RuntimeException("ERROR: Email is null");
        }
    }
}
