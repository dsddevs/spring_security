package dsd.jwt.service.validation;

import dsd.jwt.data.request.UserRegistrationRequest;
import org.springframework.stereotype.Service;

@Service
public class UserValidatorInService implements UserValidatorService {
    @Override
    public boolean isFullNameEmpty(UserRegistrationRequest request) {
        return request.getFullName() == null || request.getFullName().isEmpty();
    }

    @Override
    public boolean isEmailEmpty(UserRegistrationRequest request) {
        return request.getEmail() == null || request.getEmail().isEmpty();
    }

    @Override
    public boolean isPasswordEmpty(UserRegistrationRequest request) {
        return request.getPassword() == null || request.getPassword().isEmpty();
    }
}
