package xyz.pangosoft.encinalbackend.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "blocks")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "blockregister",
                procedureName = "SP_BLOCK_REGISTER",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "PBLOCK_NUMBER", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "PCAPACITY", type = Integer.class),
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
        this.blockNumber = blockNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    private static final long serialVersionUID = 1L;
}
