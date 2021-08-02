package ua.ivan.provider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.ivan.provider.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
