package xyz.pangosoft.encinalbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import xyz.pangosoft.encinalbackend.models.Payment;

import java.util.Date;
import java.util.List;

public interface IPaymentRepository extends JpaRepository<Payment, Integer> {
}
