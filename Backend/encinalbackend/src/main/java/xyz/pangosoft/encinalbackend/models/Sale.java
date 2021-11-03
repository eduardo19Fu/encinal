package xyz.pangosoft.encinalbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "sales")
public class Sale implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer saleId;
    private Double total;

    @Temporal(TemporalType.TIMESTAMP)
    private Date saleDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Seller seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_type_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private SaleType saleType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Status status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "terrain_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Terrain terrain;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "sale")
    @JsonIgnoreProperties({ "sale","hibernateLazyInitializer", "handler" })
    private PaymentAgreement paymentAgreement;


    @PrePersist
    public void saleDate(){
        this.saleDate = new Date();
    }

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public SaleType getSaleType() {
        return saleType;
    }

    public void setSaleType(SaleType saleType) {
        this.saleType = saleType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public PaymentAgreement getPaymentAgreement() {
        return paymentAgreement;
    }

    public void setPaymentAgreement(PaymentAgreement paymentAgreement) {
        this.paymentAgreement = paymentAgreement;
    }

    private static final long serialVersionUID = 1L;

}
