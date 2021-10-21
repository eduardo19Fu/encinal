package xyz.pangosoft.encinalbackend.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pangosoft.encinalbackend.models.PaymentAgreement;
import xyz.pangosoft.encinalbackend.models.Status;
import xyz.pangosoft.encinalbackend.repositories.IPaymentAgreementRepository;
import xyz.pangosoft.encinalbackend.services.IPaymentAgreementService;

import java.util.List;

@Service
public class PaymentAgreementServiceImpl implements IPaymentAgreementService {

    @Autowired
    private IPaymentAgreementRepository paymentAgreementRepository;

    @Override
    public List<PaymentAgreement> listPaymentAgreements() {
        return paymentAgreementRepository.findAll();
    }

    @Override
    public PaymentAgreement singlePaymentAgreement(Integer id) {
        return paymentAgreementRepository.findById(id).orElse(null);
    }

    @Override
    public PaymentAgreement save(PaymentAgreement paymentAgreement) {
        return paymentAgreementRepository.save(paymentAgreement);
    }

    @Override
    public List<PaymentAgreement> listPaymentAgreements(Status status) {
        return paymentAgreementRepository.findByStatus(status);
    }

    @Override
    public void delete(Integer id) {
        paymentAgreementRepository.deleteById(id);
    }
}
