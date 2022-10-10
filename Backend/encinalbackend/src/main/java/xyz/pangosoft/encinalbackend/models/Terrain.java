package xyz.pangosoft.encinalbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "terrains")
public class Terrain implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer terrainId;
    @NotNull(message = "El número de Lote es obligatorio.")
    private String terrainNumber;
    @NotNull(message = "El precio del Lote es obligatorio.")
    private Double price;
    @NotNull(message = "El largo del Lote es obligatorio.")
    private Double length;
    @NotNull(message = "El ancho del Lote es obligatorio.")
    private Double width;
    @NotNull(message = "El área del Lote es obligatorio.")
    private Double area;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "block_id")
    @JsonIgnoreProperties({"terrains", "hibernateLazyInitializer", "handler"})
    private Block block;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "terrain")
//    @JsonIgnoreProperties(value = {"terrain","hibernateLazyInitializer", "handler"}, allowSetters = true)
//    private List<Sale> sales;

    public Integer getTerrainId() {
        return terrainId;
    }

    public void setTerrainId(Integer terrainId) {
        this.terrainId = terrainId;
    }

    public String getTerrainNumber() {
        return terrainNumber;
    }

    public void setTerrainNumber(String terrainNumber) {
        this.terrainNumber = terrainNumber.toUpperCase();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

//    public Sale getSale() {
//        return sale;
//    }
//
//    public void setSale(Sale sale) {
//        this.sale = sale;
//    }

    private static final long serialVersionUID = 1L;
}
