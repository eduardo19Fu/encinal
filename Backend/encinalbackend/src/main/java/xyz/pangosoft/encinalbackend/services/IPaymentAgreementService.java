package xyz.pangosoft.encinalbackend.services;

import xyz.pangosoft.encinalbackend.models.PaymentAgreement;
import xyz.pangosoft.encinalbackend.models.Status;

import java.util.List;

public interface IPaymentAgreementService {

    public List<PaymentAgreement> listPaymentAgreements();

    public List<PaymentAgreement> listPaymentAgreements(Status status);

    public PaymentAgreement singlePaymentAgreement(Integer id);

    public PaymentAgreement save(PaymentAgreement paymentAgreement);

    public void delete(Integer id);
}
