package xyz.pangosoft.encinalbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.pangosoft.encinalbackend.models.SaleType;

public interface ISaleTypeRepository extends JpaRepository<SaleType, Integer> {
}
