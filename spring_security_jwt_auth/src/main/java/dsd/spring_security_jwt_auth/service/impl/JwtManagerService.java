package dsd.spring_security_jwt_auth.service.impl;

import dsd.spring_security_jwt_auth.model.entity.TokenEntity;
import dsd.spring_security_jwt_auth.model.entity.UserEntity;
import dsd.spring_security_jwt_auth.model.repo.TokenRepo;
import dsd.spring_security_jwt_auth.service.IJwtManagerService;
import dsd.spring_security_jwt_auth.types.TokenType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class JwtManagerService implements IJwtManagerService {

    private final TokenRepo tokenRepo;

    @Override
    public void saveUserToken(UserEntity user, String jwtToken) {
        var token = TokenEntity.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepo.save(token);
    }

    @Override
    public void revokeAllUserTokens(UserEntity user) {
        var validUserTokens = tokenRepo.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepo.saveAll(validUserTokens);
    }
}
