package dsd.jwt.service.validation;

import dsd.jwt.data.request.UserRegistrationRequest;

public interface UserValidatorService {
    boolean isFullNameEmpty(UserRegistrationRequest request);
    boolean isEmailEmpty(UserRegistrationRequest request);
    boolean isPasswordEmpty(UserRegistrationRequest request);
}
