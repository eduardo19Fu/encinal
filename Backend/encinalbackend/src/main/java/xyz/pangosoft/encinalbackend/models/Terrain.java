package xyz.pangosoft.encinalbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
    private Double height;
    @NotNull(message = "El ancho del Lote es obligatorio.")
    private Double weight;
    @NotNull(message = "El área del Lote es obligatorio.")
    private Double area;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "block_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Block block;

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
        this.terrainNumber = terrainNumber;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
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

    private static final long serialVersionUID = 1L;
}
