package dsd.jwt.service.auth;

import dsd.jwt.data.request.UserAuthRequest;
import dsd.jwt.data.request.UserRegistrationRequest;
import dsd.jwt.data.response.ServerAuthResponse;
import dsd.jwt.data.response.ServerAuthResponseData;
import dsd.jwt.exception.UserAlreadyExistsException;
import dsd.jwt.model.entity.UserEntity;
import dsd.jwt.model.repository.UserRepository;
import dsd.jwt.service.crud.JwtCrudService;
import dsd.jwt.service.crud.UserCrudService;
import dsd.jwt.service.generation.JwtGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserAuthInService implements UserAuthService {

    private final UserRepository userRepository;
    private final UserCrudService userCrud;
    private final JwtGeneratorService jwtGenerator;
    private final JwtCrudService jwtCrud;
    private final AuthenticationManager authenticationManager;

    @Override
    public ServerAuthResponse registerUser(UserRegistrationRequest request) throws Exception {
        if (userCrud.isUserExisted(request.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists");
        }
        UserEntity newUser = userCrud.createUser(request);
        UserEntity savedUser = userRepository.save(newUser);
        String accessJwt = jwtGenerator.generateAccessJwt(newUser);
        String refreshJwt = jwtGenerator.generateRefreshJwt(newUser);
        jwtCrud.saveJwtToDb(savedUser, accessJwt);
        return ServerAuthResponseData.builder()
                .accessJwt(accessJwt)
                .refreshJwt(refreshJwt)
                .success(true)
                .build();
    }

    @Override
    public ServerAuthResponse authenticateUser(UserAuthRequest request) throws Exception {
        String email = request.getEmail();
        String password = request.getPassword();
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        authenticationManager.authenticate(authentication);
        UserEntity user = userCrud.getUserByEmail(email);
        String accessJwt = jwtGenerator.generateAccessJwt(user);
        String refreshJwt = jwtGenerator.generateRefreshJwt(user);
        jwtCrud.saveAllRevokedJwtToDb(user);
        jwtCrud.saveJwtToDb(user, accessJwt);
        return ServerAuthResponseData.builder()
                .accessJwt(accessJwt)
                .refreshJwt(refreshJwt)
                .success(true)
                .build();
    }
}
