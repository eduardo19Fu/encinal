package xyz.pangosoft.encinalbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import xyz.pangosoft.encinalbackend.models.Client;
import xyz.pangosoft.encinalbackend.models.PaymentAgreement;
import xyz.pangosoft.encinalbackend.models.Status;

import java.util.List;

public interface IPaymentAgreementRepository extends JpaRepository<PaymentAgreement, Integer> {

    @Query("Select pa from PaymentAgreement pa where pa.status = :status")
    List<PaymentAgreement> findByStatus(@Param("status") Status status);

    @Query(value = "Select pa.* from payments_agreements as pa " +
            "join sales as sa on sa.sale_id = pa.sale_id " +
            "join clients as cl on cl.client_id = sa.client_id " +
            "join status as st on pa.status_id = st.status_id " +
            "where cl.client_id = ?1 and st.status in('Activo','En Mora') and st.description = 'Payment Agreement'", nativeQuery = true)
    List<PaymentAgreement> findAllByClient(Integer clientId);

    @Query(value = "Select pa from PaymentAgreement pa where pa.sale.client = :client and pa.status = :status")
    List<PaymentAgreement> findAllByClient2(@Param("client") Client client, @Param("status") Status status);

    @Query(value = "Select sum(principal_value) from payments where payment_agreement_id = ?1 and status_id in(22,25)", nativeQuery = true)
    Double principalRemaining(Integer paymentAgreementId);

    @Query(value = "Select payment_total from payments where payment_agreement_id = ?1 limit 1", nativeQuery = true)
    Double fee(Integer paymentAgreementId);
}
