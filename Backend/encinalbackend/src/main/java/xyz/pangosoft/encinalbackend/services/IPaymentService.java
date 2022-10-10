package xyz.pangosoft.encinalbackend.services;

import xyz.pangosoft.encinalbackend.models.Payment;

public interface IPaymentService {

    public Payment save(Payment payment);

    public Payment getPaymentAbove(Long paymentId);

    public Integer getTotalFees(Long agreement_id);

}
