package dsd.jwt.filter;

import dsd.jwt.exception.JwtAuthenticationException;
import dsd.jwt.service.extraction.JwtExtractorService;
import dsd.jwt.service.validation.JwtValidatorService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtExtractorService jwtExtractor;
    private final JwtValidatorService jwtValidator;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String accessJwt = jwtExtractor.extractJwtByRequest(request);
            if (accessJwt != null && shouldAuthenticate(accessJwt.trim())) {
                authenticate(request, accessJwt);
            }
        } catch (JwtAuthenticationException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage());
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean shouldAuthenticate(String accessJwt) {
        String email = jwtExtractor.extractEmailByJwt(accessJwt);
        return email != null && SecurityContextHolder.getContext().getAuthentication() == null;
    }

    private void authenticate(HttpServletRequest request, String accessJwt) {
        String email = jwtExtractor.extractEmailByJwt(accessJwt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        validateAndSetAuthentication(request, accessJwt, userDetails);
    }

    private void validateAndSetAuthentication(HttpServletRequest request, String jwt, UserDetails userDetails) {
        try {
            if (!jwtValidator.isJwtValid(jwt, userDetails)) {
                throw new JwtAuthenticationException("JWT validation failed");
            }
            setAuthenticationInSecurityContext(request, userDetails);
        } catch (Exception e) {
            throw new JwtAuthenticationException(e.getMessage());
        }
    }

    private void setAuthenticationInSecurityContext(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authJwt = createAuthenticationJwt(userDetails);
        authJwt.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authJwt);
    }

    private UsernamePasswordAuthenticationToken createAuthenticationJwt(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}

