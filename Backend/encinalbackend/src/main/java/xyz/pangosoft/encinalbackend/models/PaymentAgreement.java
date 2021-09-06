package xyz.pangosoft.encinalbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "payments_agreements")
public class PaymentAgreement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentAgreementId;
    private Double interestRate;
    private Double totalAgreement;
    private Integer totalPayments;

    @OneToOne
    @JoinColumn(name = "sale_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Sale sale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Status status;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_agreement_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private List<Payment> payments;

    public PaymentAgreement(){
        this.payments = new ArrayList<>();
    }

    public Integer getPaymentAgreementId() {
        return paymentAgreementId;
    }

    public void setPaymentAgreementId(Integer paymentAgreementId) {
        this.paymentAgreementId = paymentAgreementId;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getTotalAgreement() {
        return totalAgreement;
    }

    public void setTotalAgreement(Double totalAgreement) {
        this.totalAgreement = totalAgreement;
    }

    public Integer getTotalPayments() {
        return totalPayments;
    }

    public void setTotalPayments(Integer totalPayments) {
        this.totalPayments = totalPayments;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    private static final long serialVersionUID = 1L;
}
