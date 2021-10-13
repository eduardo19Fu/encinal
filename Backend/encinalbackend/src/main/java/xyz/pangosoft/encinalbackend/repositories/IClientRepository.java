package xyz.pangosoft.encinalbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.pangosoft.encinalbackend.models.Client;
import xyz.pangosoft.encinalbackend.models.Status;

import java.util.List;

public interface IClientRepository extends JpaRepository<Client, Integer> {

    List<Client> findByStatusNot(Status status);


}
