package dsd.jwt.model.repository;

import dsd.jwt.model.entity.JwtEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JwtRepository extends JpaRepository<JwtEntity, Long> {
    @Query(value = """
            SELECT j FROM JwtEntity j
            INNER JOIN UserEntity u\s
            ON j.user.id = u.id\s
            WHERE u.id = :userId AND (j.expired = false OR j.revoked = false)\s
                        \s""")
    List<JwtEntity> findAllByUserId(long userId);

    Optional<JwtEntity> findByJwt(String jwt);
}
