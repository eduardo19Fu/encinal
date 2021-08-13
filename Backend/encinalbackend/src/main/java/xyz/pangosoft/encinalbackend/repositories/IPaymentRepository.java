package xyz.pangosoft.encinalbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.pangosoft.encinalbackend.models.Payment;

public interface IPaymentRepository extends JpaRepository<Payment, Integer> {
}
