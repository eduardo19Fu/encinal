package xyz.pangosoft.encinalbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "transfers")
public class Transfer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transferId;
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_client")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Client fromClient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_client")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Client toClient;

    public Integer getTransferId() {
        return transferId;
    }

    public void setTransferId(Integer transferId) {
        this.transferId = transferId;
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

    public Client getFromClient() {
        return fromClient;
    }

    public void setFromClient(Client fromClient) {
        this.fromClient = fromClient;
    }

    public Client getToClient() {
        return toClient;
    }

    public void setToClient(Client toClient) {
        this.toClient = toClient;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private final static long serialVersionUID = 1L;
}
