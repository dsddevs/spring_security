package dsd.spring_security_jwt_auth.utils;

import dsd.spring_security_jwt_auth.service.IExtractorService;
import dsd.spring_security_jwt_auth.service.IValidatorService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final IExtractorService extractor;
    private final IValidatorService validator;
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(IExtractorService extractor,
                         @Lazy IValidatorService validator,
                         UserDetailsService userDetailsService) {
        this.extractor = extractor;
        this.validator = validator;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String jwt = extractor.extractJwt(request);
        if (jwt == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String email = extractor.extractEmail(jwt.trim());
        var authContext = SecurityContextHolder.getContext().getAuthentication();
        if (email != null && authContext == null) {
            processJwtAuthentication(request, jwt, email);
        }
        filterChain.doFilter(request, response);
    }

    private void processJwtAuthentication(HttpServletRequest request, String jwt, String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (validator.isTokenValid(jwt, userDetails)) {
            UsernamePasswordAuthenticationToken authToken = createAuthenticationToken(userDetails);
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        } else {
            throw new RuntimeException("ERROR: JWT is not valid - process jwt authentication");
        }
    }

    private UsernamePasswordAuthenticationToken createAuthenticationToken(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
