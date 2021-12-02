package xyz.pangosoft.encinalbackend.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "receipt_type")
public class ReceiptType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer receiptTypeId;
    private String description;

    public Integer getReceiptTypeId() {
        return receiptTypeId;
    }

    public void setReceiptTypeId(Integer receiptTypeId) {
        this.receiptTypeId = receiptTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private static final long serialVersionUID = 1L;
}
