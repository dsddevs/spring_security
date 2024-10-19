package dsd.spring_security_jwt_auth.service;

import dsd.spring_security_jwt_auth.model.entity.UserEntity;

public interface IJwtManagerService {
    void saveUserToken(UserEntity user, String jwtToken);

    void revokeAllUserTokens(UserEntity user);

}
