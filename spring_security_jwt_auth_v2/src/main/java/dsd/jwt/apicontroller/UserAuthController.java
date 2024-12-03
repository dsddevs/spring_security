package dsd.jwt.apicontroller;

import dsd.jwt.data.request.UserAuthRequest;
import dsd.jwt.data.request.UserRegistrationRequest;
import dsd.jwt.data.response.ServerApiResponseData;
import dsd.jwt.data.response.ServerAuthResponse;
import dsd.jwt.service.auth.UserAuthService;
import dsd.jwt.service.refresher.JwtRefresherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserAuthController {

    private final UserAuthService userService;
    private final JwtRefresherService jwtRefresherService;

    @PostMapping("/signup")
    public ResponseEntity<ServerApiResponseData<ServerAuthResponse>> signUp(
            @Valid @RequestBody UserRegistrationRequest request) throws Exception {
        ServerAuthResponse responseData = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(sendSuccessResponse(responseData));
    }

    @PostMapping("/signin")
    public ResponseEntity<ServerApiResponseData<ServerAuthResponse>> signIn(
            @Valid @RequestBody UserAuthRequest request) {
        try {
            ServerAuthResponse responseData = userService.authenticateUser(request);
            return ResponseEntity.ok(sendSuccessResponse(responseData));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(sendErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ServerApiResponseData<String>> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {
        try {
            jwtRefresherService.refreshJwt(request, response);
            var successResponse = ServerApiResponseData.<String>builder().data("Jwt refreshed successfully").build();
            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {
            var errorResponse = ServerApiResponseData.<String>builder().data("Jwt refreshed is failed").build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    private ServerApiResponseData<ServerAuthResponse> sendSuccessResponse(ServerAuthResponse responseData) {
        return ServerApiResponseData.<ServerAuthResponse>builder().success(true).data(responseData).build();
    }

    private ServerApiResponseData<ServerAuthResponse> sendErrorResponse(String errorMessage) {
        return ServerApiResponseData.<ServerAuthResponse>builder().success(false).errorMessage(errorMessage).build();
    }

}
