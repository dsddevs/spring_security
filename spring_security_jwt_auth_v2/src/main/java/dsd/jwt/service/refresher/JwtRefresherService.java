package dsd.jwt.service.refresher;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface JwtRefresherService {
    void refreshJwt(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
