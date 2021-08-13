package xyz.pangosoft.encinalbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.pangosoft.encinalbackend.models.Status;

import java.util.Optional;

public interface IStatusRepository extends JpaRepository<Status, Integer> {

    Optional<Status> findByStatus(String status);
}
