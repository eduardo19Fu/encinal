package xyz.pangosoft.encinalbackend.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sales_types")
public class SaleType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer saleTypeId;
    private String saleType;
    private String description;

    public Integer getSaleTypeId() {
        return saleTypeId;
    }

    public void setSaleTypeId(Integer saleTypeId) {
        this.saleTypeId = saleTypeId;
    }

    public String getSaleType() {
        return saleType;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private static final long serialVersionUID = 1L;
}
