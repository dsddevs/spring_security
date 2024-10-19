package dsd.spring_security_jwt_auth.service;

import dsd.spring_security_jwt_auth.dto.AuthResponseDto;
import dsd.spring_security_jwt_auth.dto.SignInRequestDto;
import dsd.spring_security_jwt_auth.dto.SignUpRequestDto;

public interface IAuthenticationService {
    AuthResponseDto signUp(SignUpRequestDto request);

    AuthResponseDto sighIn(SignInRequestDto request);
}
