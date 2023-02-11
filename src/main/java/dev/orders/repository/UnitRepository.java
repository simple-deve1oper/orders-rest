package dev.orders.repository;

import dev.orders.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    Optional<Unit> findByShortName(String shortName);
    void deleteByShortName(String shortName);
}
