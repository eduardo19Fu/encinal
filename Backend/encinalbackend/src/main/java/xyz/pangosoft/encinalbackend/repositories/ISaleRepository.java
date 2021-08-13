package xyz.pangosoft.encinalbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.pangosoft.encinalbackend.models.Sale;

public interface ISaleRepository extends JpaRepository<Sale, Integer> {
}
