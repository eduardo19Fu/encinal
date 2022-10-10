package xyz.pangosoft.encinalbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.pangosoft.encinalbackend.models.Correlative;
import xyz.pangosoft.encinalbackend.models.Status;
import xyz.pangosoft.encinalbackend.models.User;

import java.util.Optional;

public interface ICorrelativeRepository extends JpaRepository<Correlative, Integer> {

    Optional<Correlative> findByAssignedToAndStatus(User assignedTo, Status status);
}
