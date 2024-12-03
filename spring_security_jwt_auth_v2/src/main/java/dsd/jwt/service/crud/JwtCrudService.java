package dsd.jwt.service.crud;

import dsd.jwt.model.entity.UserEntity;

public interface JwtCrudService {
    void saveJwtToDb(UserEntity user, String jwt) throws Exception;

    void saveAllRevokedJwtToDb(UserEntity user) throws Exception;
}
