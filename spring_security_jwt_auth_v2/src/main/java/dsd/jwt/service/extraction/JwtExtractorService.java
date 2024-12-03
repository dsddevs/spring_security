package dsd.jwt.service.extraction;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;

public interface JwtExtractorService {
    String extractEmailByJwt(String jwt);
    Date extractExpirationByJwt(String jwt);
    String extractJwtByRequest(HttpServletRequest request);
}
