package xyz.pangosoft.encinalbackend.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pangosoft.encinalbackend.models.Payment;
import xyz.pangosoft.encinalbackend.repositories.IPaymentRepository;
import xyz.pangosoft.encinalbackend.services.IPaymentService;

@Service
public class PaymentServiceImpl implements IPaymentService {

    @Autowired
    private IPaymentRepository paymentRepository;

    @Override
    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentAbove(Long paymentId) {
        return paymentRepository.findPaymentAbove(paymentId).orElse(null);
    }

    @Override
    public Integer getTotalFees(Long agreement_id) {
        return paymentRepository.totalFees(agreement_id);
    }
}
