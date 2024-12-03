package dsd.jwt.service.crud;

import dsd.jwt.model.entity.UserEntity;
import dsd.jwt.data.request.UserRegistrationRequest;

public interface UserCrudService {
    boolean isUserExisted(String email);
    UserEntity getUserByEmail(String email);
    UserEntity createUser(UserRegistrationRequest request);
}
