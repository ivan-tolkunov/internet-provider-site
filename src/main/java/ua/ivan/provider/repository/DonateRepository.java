package ua.ivan.provider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.ivan.provider.model.Donate;

import java.util.Optional;

public interface DonateRepository extends JpaRepository<Donate, Long> {
    Optional<Donate> findByUserId(Long id);
}
