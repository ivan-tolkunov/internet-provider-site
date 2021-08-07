package ua.ivan.provider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ua.ivan.provider.model.Packages;

import java.util.Optional;

public interface PackageRepository extends JpaRepository<Packages, Long> {
        Optional<Packages> findByUserId(Long id);
}
