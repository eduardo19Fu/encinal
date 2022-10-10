package xyz.pangosoft.encinalbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "blocks")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "blockregister",
                procedureName = "SP_BLOCK_REGISTER",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "PBLOCK_NUMBER", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "PCAPACITY", type = Integer.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "PREMAINING", type = Integer.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "PMESSAGE", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "PERROR", type = String.class)
                })
})
public class Block implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer blockId;
    private String blockNumber;
    private Integer capacity;
    private Integer remaining;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Status status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "block")
    @JsonIgnoreProperties({"block", "hibernateLazyInitializer", "handler"})
    private List<Terrain> terrains;

    @PrePersist
    public void prepersist(){
        this.createdAt = new Date();
    }

    public Integer getBlockId() {
        return blockId;
    }

    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber.toUpperCase();
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Terrain> getTerrains() {
        return terrains;
    }

    public void setTerrains(List<Terrain> terrains) {
        this.terrains = terrains;
    }

    public Integer getRemaining() {
        return remaining;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /* UTIL METHODS */
    public Integer decreaseRemaining(){
        return this.getRemaining() - 1;
    }

    @Override
    public String toString() {
        return "Block{" +
                "blockId=" + blockId +
                ", blockNumber='" + blockNumber + '\'' +
                ", capacity=" + capacity +
                ", remaining=" + remaining +
                ", createdAt=" + createdAt +
                ", status=" + status +
                ", terrains=" + terrains +
                '}';
    }

    private static final long serialVersionUID = 1L;
}
