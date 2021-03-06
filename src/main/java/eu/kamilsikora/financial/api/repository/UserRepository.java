package eu.kamilsikora.financial.api.repository;

import eu.kamilsikora.financial.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameAndEnabled(String username, Boolean enabled);

    List<User> findByUsernameOrEmail(String username, String email);
}