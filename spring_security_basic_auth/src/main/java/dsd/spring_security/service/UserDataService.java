package dsd.spring_security.service;

import dsd.spring_security.dto.UserDto;
import dsd.spring_security.model.entity.RoleEntity;
import dsd.spring_security.model.entity.UserEntity;
import dsd.spring_security.model.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;

import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserDataService implements IUserDataService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserDto createUserDetails(String username, String password, RoleEntity role) {
        try {
            UserEntity existingUser = userRepo.findByUsername(username);
            if (existingUser != null) throw new RuntimeException("Error: Username already exists in db!");
            UserEntity user = new UserEntity(username, passwordEncoder.encode(password), role);
            userRepo.save(user);
            return new UserDto(user.getUsername(), user.getPassword(), user.getRole().getRoleType());
        } catch (RuntimeException e) {
            throw e;
        }
    }

    @Override
    public String getUserDetails(UserDetails details) {
        String username = details.getUsername();
        String userAuth = details.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));
        return String.format("Username: %s - Role: %s", username, userAuth);
    }
}
