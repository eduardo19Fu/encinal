package xyz.pangosoft.encinalbackend.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "correlatives")
public class Correlative implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer correlativeId;
    private Integer initialCorrelative;
    private Integer finalCorrelative;
    private Integer currentCorrelative;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User assignedTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Status status;

    @PrePersist
    public void prepersist(){
        this.createdAt = new Date();
    }

    public Integer getCorrelativeId() {
        return correlativeId;
    }

    public void setCorrelativeId(Integer correlativeId) {
        this.correlativeId = correlativeId;
    }

    public Integer getInitialCorrelative() {
        return initialCorrelative;
    }

    public void setInitialCorrelative(Integer initialCorrelative) {
        this.initialCorrelative = initialCorrelative;
    }

    public Integer getFinalCorrelative() {
        return finalCorrelative;
    }

    public void setFinalCorrelative(Integer finalCorrelative) {
        this.finalCorrelative = finalCorrelative;
    }

    public Integer getCurrentCorrelative() {
        return currentCorrelative;
    }

    public void setCurrentCorrelative(Integer currentCorrelative) {
        this.currentCorrelative = currentCorrelative;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    private static final long serialVersionUID = 1L;
}
