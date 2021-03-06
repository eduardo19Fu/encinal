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
    private Integer paymentId;
    private Integer paymentNumber;
    private Double principalValue;
    private Double interestRateGenerated;
    private Double remainingBalance;
    private Double arrears;
    private Double paymentTotal;

    @Temporal(TemporalType.DATE)
    private Date expireDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Status status;

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
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

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", paymentNumber=" + paymentNumber +
                ", principalValue=" + principalValue +
                ", interestRateGenerated=" + interestRateGenerated +
                ", remainingBalance=" + remainingBalance +
                ", paymentTotal=" + paymentTotal +
                ", expireDate=" + expireDate +
                ", status=" + status +
                '}';
    }

    private static final long serialVersionUID = 1L;
}
