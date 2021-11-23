package xyz.pangosoft.encinalbackend.services;

import xyz.pangosoft.encinalbackend.models.Client;
import xyz.pangosoft.encinalbackend.models.PaymentAgreement;
import xyz.pangosoft.encinalbackend.models.Status;

import java.util.List;

public interface IPaymentAgreementService {

    public List<PaymentAgreement> listPaymentAgreements();

    public List<PaymentAgreement> listPaymentAgreements(Status status);

    public List<PaymentAgreement> listPaymentAgreementsByClient(Integer clientId);

    public List<PaymentAgreement> listPaymentAgreementsByClient(Client client, Status status);

    public PaymentAgreement singlePaymentAgreement(Integer id);

    public PaymentAgreement save(PaymentAgreement paymentAgreement);

    public void delete(Integer id);

    public Double getPrincipalTotal(Integer paymentAgreementId);

    public Double getFee(Integer paymentAgreementId);
}
