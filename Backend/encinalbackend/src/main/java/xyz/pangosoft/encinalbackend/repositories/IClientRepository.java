package xyz.pangosoft.encinalbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.pangosoft.encinalbackend.models.Client;
import xyz.pangosoft.encinalbackend.models.Status;

import java.util.List;
import java.util.Optional;

public interface IClientRepository extends JpaRepository<Client, Integer> {

    List<Client> findByStatusNot(Status status);

    @Query("Select c from Client c where c.id = ?1")
    Optional<Client> findByIdentification(String identification);

}
