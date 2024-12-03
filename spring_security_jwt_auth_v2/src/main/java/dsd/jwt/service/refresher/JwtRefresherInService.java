package dsd.jwt.service.refresher;

import com.fasterxml.jackson.databind.ObjectMapper;
import dsd.jwt.data.response.ServerAuthResponse;
import dsd.jwt.data.response.ServerAuthResponseData;
import dsd.jwt.exception.EmailNotFoundException;
import dsd.jwt.exception.RefreshJwtNotFoundException;
import dsd.jwt.model.entity.UserEntity;
import dsd.jwt.service.crud.JwtCrudService;
import dsd.jwt.service.crud.UserCrudInService;
import dsd.jwt.service.extraction.JwtExtractorService;
import dsd.jwt.service.generation.JwtGeneratorService;
import dsd.jwt.service.validation.JwtValidatorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtRefresherInService implements JwtRefresherService {

    private final UserCrudInService userService;
    private final JwtCrudService jwtCrud;
    private final JwtValidatorService jwtValidator;
    private final JwtGeneratorService jwtGenerator;
    private final JwtExtractorService jwtExtractor;

    @Override
    public void refreshJwt(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String refreshedJwt = jwtExtractor.extractJwtByRequest(request);
        if (refreshedJwt == null) {
            throw new RefreshJwtNotFoundException("Refresh Jwt is not found");
        }
        String email = jwtExtractor.extractEmailByJwt(refreshedJwt);
        if (email == null) {
            throw new EmailNotFoundException("Email is not found");
        }
        UserEntity user = userService.getUserByEmail(email);
        sendJwtToClient(user, refreshedJwt, response);
    }

    private void sendJwtToClient(UserEntity user, String refreshJwt, HttpServletResponse response) throws Exception {
        if (jwtValidator.isJwtValid(refreshJwt, user)) {
            String accessJwt = jwtGenerator.generateAccessJwt(user);
            jwtCrud.saveAllRevokedJwtToDb(user);
            jwtCrud.saveJwtToDb(user, accessJwt);
            ServerAuthResponse successResponse = ServerAuthResponseData.builder()
                    .accessJwt(accessJwt)
                    .refreshJwt(refreshJwt)
                    .success(true)
                    .build();
            new ObjectMapper().writeValue(response.getOutputStream(), successResponse);
        } else {
            ServerAuthResponse errorResponse = ServerAuthResponseData.builder()
                    .success(false)
                    .errorMessage("JWT is not valid")
                    .build();
            new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
        }
    }

}
