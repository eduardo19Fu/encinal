package xyz.pangosoft.encinalbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.pangosoft.encinalbackend.models.PaymentAgreement;

public interface IPaymentAgreementRepository extends JpaRepository<PaymentAgreement, Integer> {
}
