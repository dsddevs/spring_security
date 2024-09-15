package dsd.spring_security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserDataService implements IUserDataService {
    @Override
    public String getUserDetails(UserDetails details) {
        String username = details.getUsername();
        String userAuth = details.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));
        return String.format("Username: %s - Role: %s", username, userAuth);
    }
}
