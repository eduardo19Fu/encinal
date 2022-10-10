package xyz.pangosoft.encinalbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import xyz.pangosoft.encinalbackend.models.Payment;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IPaymentRepository extends JpaRepository<Payment, Long> {

    @Query(value = "Select * from payments where payment_id = (Select max(payment_id) from payments where payment_id < :payment_id)", nativeQuery = true)
    Optional<Payment> findPaymentAbove(@Param("payment_id") Long payment_id);

    @Query(value = "Select count(1) from payments where payment_agreement_id = :agreement_id and status_id not in(23,24)", nativeQuery = true)
    Integer totalFees(@Param("agreement_id") Long agreement_id);
}
