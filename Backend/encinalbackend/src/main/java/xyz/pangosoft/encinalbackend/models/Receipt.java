package xyz.pangosoft.encinalbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "receipts")
public class Receipt implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer receiptId;
    private String receiptNumber;
    private Double total;
    private Float arrearsValue;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_type_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private ReceiptType receiptType;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "receipt_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<ReceiptDetail> items;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "register_by")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User registerBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_agreement_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private PaymentAgreement paymentAgreement;

    public Receipt(){
        this.items = new ArrayList<>();
    }

    @PrePersist
    public void prepersist(){
        this.createdAt = new Date();
    }

    public Integer getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Integer receiptId) {
        this.receiptId = receiptId;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Float getArrearsValue() {
        return arrearsValue;
    }

    public void setArrears(Float arrearsValue) {
        this.arrearsValue = arrearsValue;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ReceiptType getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(ReceiptType receiptType) {
        this.receiptType = receiptType;
    }

    public List<ReceiptDetail> getItems() {
        return items;
    }

    public void setItems(List<ReceiptDetail> items) {
        this.items = items;
    }

    public User getRegisterBy() {
        return registerBy;
    }

    public void setRegisterBy(User registerBy) {
        this.registerBy = registerBy;
    }

    public PaymentAgreement getPaymentAgreement() {
        return paymentAgreement;
    }

    public void setPaymentAgreement(PaymentAgreement paymentAgreement) {
        this.paymentAgreement = paymentAgreement;
    }

    private static final long serialVersionUID = 1L;
}
