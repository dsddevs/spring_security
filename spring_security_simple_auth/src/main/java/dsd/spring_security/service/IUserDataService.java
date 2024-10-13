package dsd.spring_security.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface IUserDataService {
    String getUserDetails(UserDetails details);
}
