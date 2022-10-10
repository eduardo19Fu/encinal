package xyz.pangosoft.encinalbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "payments")
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    private Integer paymentNumber;
    private Double principalValue;
    private Double interestRateGenerated;
    private Double remainingBalance;
    private Double arrears;
    private Double paymentTotal;
    private Double provisionalPayment;

    @Temporal(TemporalType.DATE)
    private Date expireDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Status status;

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(Integer paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public Double getPrincipalValue() {
        return principalValue;
    }

    public void setPrincipalValue(Double principalValue) {
        this.principalValue = principalValue;
    }

    public Double getInterestRateGenerated() {
        return interestRateGenerated;
    }

    public void setInterestRateGenerated(Double interestRateGenerated) {
        this.interestRateGenerated = interestRateGenerated;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Double getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(Double remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    public Double getArrears() {
        return arrears;
    }

    public void setArrears(Double arrears) {
        this.arrears = arrears;
    }

    public Double getPaymentTotal() {
        return paymentTotal;
    }

    public void setPaymentTotal(Double paymentTotal) {
        this.paymentTotal = paymentTotal;
    }

    public Double getProvisionalPayment() {
        return provisionalPayment;
    }

    public void setProvisionalPayment(Double provisionalPayment) {
        this.provisionalPayment = provisionalPayment;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Payment{");
        sb.append("paymentId=").append(paymentId);
        sb.append(", paymentNumber=").append(paymentNumber);
        sb.append(", principalValue=").append(principalValue);
        sb.append(", interestRateGenerated=").append(interestRateGenerated);
        sb.append(", remainingBalance=").append(remainingBalance);
        sb.append(", arrears=").append(arrears);
        sb.append(", paymentTotal=").append(paymentTotal);
        sb.append(", provisionalPayment=").append(provisionalPayment);
        sb.append(", expireDate=").append(expireDate);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }

    private static final long serialVersionUID = 1L;
}
