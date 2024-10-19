package dsd.spring_security_jwt_auth.service.impl;

import dsd.spring_security_jwt_auth.dto.AuthResponseDto;
import dsd.spring_security_jwt_auth.dto.SignInRequestDto;
import dsd.spring_security_jwt_auth.dto.SignUpRequestDto;
import dsd.spring_security_jwt_auth.model.entity.UserEntity;
import dsd.spring_security_jwt_auth.model.repo.UserRepo;
import dsd.spring_security_jwt_auth.service.IAuthenticationService;
import dsd.spring_security_jwt_auth.service.IGeneratorService;
import dsd.spring_security_jwt_auth.service.IJwtManagerService;
import dsd.spring_security_jwt_auth.types.RoleType;
import dsd.spring_security_jwt_auth.utils.IUserChecker;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService implements IAuthenticationService, IUserChecker {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final IGeneratorService generator;
    private final IJwtManagerService jwtManager;

    @Override
    public AuthResponseDto signUp(SignUpRequestDto request) {
        try {
            var user = registerUser(request);
            var savedUser = userRepo.save(user);
            var accessToken = generator.generateToken(user);
            var refreshToken = generator.generateRefreshToken(user);

            jwtManager.saveUserToken(savedUser, accessToken);

            return AuthResponseDto
                    .builder()
                    .success(true)
                    .message("SUCCESS: User registered")
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

        } catch (Exception e) {
            return AuthResponseDto
                    .builder()
                    .success(false)
                    .message("ERROR: User registration failed: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public AuthResponseDto sighIn(SignInRequestDto request) {
        try {
            String email = request.getEmail();
            String password = request.getPassword();
            var userAuthToken = new UsernamePasswordAuthenticationToken(email, password);
            authManager.authenticate(userAuthToken);

            var user = checkUserExisting(email, userRepo);
            var jwtToken = generator.generateToken(user);
            var refreshToken = generator.generateRefreshToken(user);

            jwtManager.revokeAllUserTokens(user);
            jwtManager.saveUserToken(user, jwtToken);

            return AuthResponseDto
                    .builder()
                    .success(true)
                    .message("SUCCESS: User authenticated")
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (Exception e) {
            return AuthResponseDto
                    .builder()
                    .success(false)
                    .message("ERROR: User authentication failed: " + e.getMessage())
                    .build();
        }
    }

    private UserEntity registerUser(SignUpRequestDto request) {
        String username = request.getUsername();
        String email = request.getEmail();
        String password = passwordEncoder.encode(request.getPassword());
        return UserEntity.builder()
                .username(username)
                .email(email)
                .password(password)
                .role(RoleType.USER)
                .build();
    }

}
