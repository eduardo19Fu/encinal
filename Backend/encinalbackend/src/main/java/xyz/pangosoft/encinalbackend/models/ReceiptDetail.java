package xyz.pangosoft.encinalbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/*@Entity
@Table(name = "receipt_details")*/
public class ReceiptDetail implements Serializable {

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)*/
    private Integer receiptDetailId;
    private Double subtotal;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })*/
    private Payment payment;

    public Integer getReceiptDetailId() {
        return receiptDetailId;
    }

    public void setReceiptDetailId(Integer receiptDetailId) {
        this.receiptDetailId = receiptDetailId;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    private static final long serialVersionUID = 1L;
}
