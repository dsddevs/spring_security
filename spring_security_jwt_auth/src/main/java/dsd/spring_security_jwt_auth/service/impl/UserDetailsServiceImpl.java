package dsd.spring_security_jwt_auth.service.impl;

import dsd.spring_security_jwt_auth.model.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            return userRepo
                    .findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("ERROR: User not found"));
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
