package xyz.pangosoft.encinalbackend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import xyz.pangosoft.encinalbackend.models.Sale;

import java.util.Date;

public interface ISaleRepository extends JpaRepository<Sale, Integer> {

    Page<Sale> findBySaleDateBetween(Date iniDate, Date endDAte, Pageable pageable);
}
