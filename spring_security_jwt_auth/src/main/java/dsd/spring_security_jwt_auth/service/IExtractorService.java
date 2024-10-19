package dsd.spring_security_jwt_auth.service;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;

public interface IExtractorService {
    String extractEmail(String token);

    Date extractExpiration(String token);

    String extractJwt(HttpServletRequest request);

}
