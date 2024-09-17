package dsd.spring_security.model.repo;

import dsd.spring_security.model.entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
    @EntityGraph(attributePaths = {"role"})
    UserEntity findByUsername(String username);
}