package xyz.pangosoft.encinalbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.pangosoft.encinalbackend.models.Transfer;

public interface ITransferRepository extends JpaRepository<Transfer, Integer> {
}
