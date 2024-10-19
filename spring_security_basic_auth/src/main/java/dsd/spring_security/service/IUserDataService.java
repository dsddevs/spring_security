package dsd.spring_security.service;

import dsd.spring_security.model.entity.RoleEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUserDataService {
    void createUserDetails(String username, String password, RoleEntity role);
    String getUserDetails(UserDetails details);
}
