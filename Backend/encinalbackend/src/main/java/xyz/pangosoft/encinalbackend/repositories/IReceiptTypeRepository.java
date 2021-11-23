package xyz.pangosoft.encinalbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.pangosoft.encinalbackend.models.ReceiptType;

public interface IReceiptTypeRepository extends JpaRepository<ReceiptType, Integer> {
}