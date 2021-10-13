package xyz.pangosoft.encinalbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import xyz.pangosoft.encinalbackend.models.PaymentAgreement;
import xyz.pangosoft.encinalbackend.models.Status;

import java.util.List;

public interface IPaymentAgreementRepository extends JpaRepository<PaymentAgreement, Integer> {

    @Query("Select pa from PaymentAgreement pa where pa.status = :status")
    List<PaymentAgreement> findByStatus(@Param("status") Status status);
}
