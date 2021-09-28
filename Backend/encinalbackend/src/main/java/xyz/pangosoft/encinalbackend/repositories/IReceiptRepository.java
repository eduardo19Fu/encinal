package xyz.pangosoft.encinalbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.pangosoft.encinalbackend.models.Receipt;

public interface IReceiptRepository extends JpaRepository<Receipt, Integer> {
}
