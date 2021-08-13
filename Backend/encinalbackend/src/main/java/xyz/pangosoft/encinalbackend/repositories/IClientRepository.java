package xyz.pangosoft.encinalbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.pangosoft.encinalbackend.models.Client;

public interface IClientRepository extends JpaRepository<Client, Integer> {
}
