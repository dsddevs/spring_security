package dsd.spring_security_jwt_auth.utils;

import dsd.spring_security_jwt_auth.model.entity.UserEntity;
import dsd.spring_security_jwt_auth.model.repo.UserRepo;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IUserChecker {
    default UserEntity checkUserExisting(String username, UserRepo userRepo){
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Error: UserEntity not found!"));
    }
}
