package xyz.pangosoft.encinalbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.pangosoft.encinalbackend.models.Seller;

public interface ISellerRepository extends JpaRepository<Seller, Integer> {
}
