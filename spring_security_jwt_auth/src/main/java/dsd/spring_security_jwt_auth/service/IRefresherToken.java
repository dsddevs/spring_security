package dsd.spring_security_jwt_auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface IRefresherToken {
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
