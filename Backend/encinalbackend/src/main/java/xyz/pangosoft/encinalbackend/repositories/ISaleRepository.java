package xyz.pangosoft.encinalbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.pangosoft.encinalbackend.models.Sale;

import java.util.Date;
import java.util.List;

public interface ISaleRepository extends JpaRepository<Sale, Integer> {

    List<Sale> findBySaleDateBetween(Date iniDate, Date endDate);
}
