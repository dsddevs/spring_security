package dsd.jwt.service.auth;

import dsd.jwt.data.request.UserAuthRequest;
import dsd.jwt.data.response.ServerAuthResponse;
import dsd.jwt.data.request.UserRegistrationRequest;

public interface UserAuthService {
    ServerAuthResponse registerUser(UserRegistrationRequest request) throws Exception;
    ServerAuthResponse authenticateUser(UserAuthRequest request) throws Exception;
}
