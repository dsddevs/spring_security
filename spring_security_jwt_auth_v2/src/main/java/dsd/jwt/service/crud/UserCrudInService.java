package dsd.jwt.service.crud;

import dsd.jwt.data.request.UserRegistrationRequest;
import dsd.jwt.model.entity.UserEntity;
import dsd.jwt.model.repository.UserRepository;
import dsd.jwt.type.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserCrudInService implements UserCrudService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean isUserExisted(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserEntity createUser(UserRegistrationRequest request) {
        return UserEntity.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(RoleType.USER)
                .build();
    }
}
