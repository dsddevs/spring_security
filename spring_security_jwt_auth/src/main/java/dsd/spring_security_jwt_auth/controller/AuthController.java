package dsd.spring_security_jwt_auth.controller;

import dsd.spring_security_jwt_auth.dto.AuthResponseDto;
import dsd.spring_security_jwt_auth.dto.SignInRequestDto;
import dsd.spring_security_jwt_auth.dto.SignUpRequestDto;
import dsd.spring_security_jwt_auth.service.IAuthenticationService;
import dsd.spring_security_jwt_auth.service.IRefresherToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AuthController {

    private final IAuthenticationService authService;
    private final IRefresherToken refresher;

    @RequestMapping()
    public ResponseEntity<String> connectServer() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body("SUCCESS: Server connected");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("ERROR: Server not connected: " + e.getMessage());
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDto> register(@RequestBody SignUpRequestDto request) {
        try {
            return ResponseEntity.ok(authService.signUp(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponseDto> authenticate(@RequestBody SignInRequestDto request) {
        try {
            return ResponseEntity.ok(authService.sighIn(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            refresher.refreshToken(request, response);
            return ResponseEntity.ok("Success: Token was refreshed");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
