package xyz.pangosoft.encinalbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "clients")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "clientRegister",
                procedureName = "SP_CLIENT_REGISTER",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "PFIRST_NAME", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "PMIDDLE_NAME", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "PLAST_NAME", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "PNIT", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "PID", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "PIDENTIFICACION", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "PIDENTIFICATION_TYPE_ID", type = Integer.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "PTEL_NUMBER", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "PEMAIL", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "PBIRTH_DATE", type = Date.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "PADDRESS", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "PSTATUS_ID", type = Integer.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "PMESSAGE", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "PERROR", type = String.class)
                })
})
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clientId;

    private String firstName;
    private String middleName;
    private String lastName;
    private String nit;
    private String id;
    private String telNumber;
    private String email;
    private String address;
    private Double amountOutstanding;
    private Double principalOutstanding;
    private Double interestOutstanding;

    @Transient
    private boolean underage;

    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "identification_type_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private IdentificationType identificationType;

    @PrePersist
    public void initConfig() {
        this.createdAt = new Date();
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getAmountOutstanding() {
        return amountOutstanding;
    }

    public void setAmountOutstanding(Double amountOutstanding) {
        this.amountOutstanding = amountOutstanding;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public IdentificationType getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(IdentificationType identificationType) {
        this.identificationType = identificationType;
    }

    public boolean isUnderage() {
        return underage;
    }

    public void setUnderage(boolean underage) {
        this.underage = underage;
    }

    public Double getPrincipalOutstanding() {
        return principalOutstanding;
    }

    public void setPrincipalOutstanding(Double principalOutstanding) {
        this.principalOutstanding = principalOutstanding;
    }

    public Double getInterestOutstanding() {
        return interestOutstanding;
    }

    public void setInterestOutstanding(Double interestOutstanding) {
        this.interestOutstanding = interestOutstanding;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nit='" + nit + '\'' +
                ", id='" + id + '\'' +
                ", telNumber='" + telNumber + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", amountOutstanding=" + amountOutstanding +
                ", principalOutstanding=" + principalOutstanding +
                ", interestOutstanding=" + interestOutstanding +
                ", underage=" + underage +
                ", birthDate=" + birthDate +
                ", createdAt=" + createdAt +
                ", status=" + status +
                ", identificationType=" + identificationType +
                '}';
    }

    private static final long serialVersionUID = 1L;
}
