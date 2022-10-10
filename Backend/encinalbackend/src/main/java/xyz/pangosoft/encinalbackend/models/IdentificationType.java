package xyz.pangosoft.encinalbackend.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Locale;

@Entity
@Table(name = "identification_types")
public class IdentificationType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer identificationTypeId;

    private String name;

    public Integer getIdentificationTypeId() {
        return identificationTypeId;
    }

    public void setIdentificationTypeId(Integer identificationTypeId) {
        this.identificationTypeId = identificationTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    private static final long serialVersionUID = 1L;
}
