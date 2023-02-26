package com.atease.booking.domain;

import com.atease.booking.domain.enumeration.PaymentDetailType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PaymentDetails.
 */
@Entity
@Table(name = "payment_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaymentDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "total")
    private Double total;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PaymentDetailType type;

    @Column(name = "modified_date")
    private LocalDate modifiedDate;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "created_by")
    private String createdBy;

    @ManyToOne
    @JsonIgnoreProperties(value = { "reservation", "paymentDetails", "paymentTypes" }, allowSetters = true)
    private Payment payment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PaymentDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public PaymentDetails quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return this.unitPrice;
    }

    public PaymentDetails unitPrice(Double unitPrice) {
        this.setUnitPrice(unitPrice);
        return this;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getTotal() {
        return this.total;
    }

    public PaymentDetails total(Double total) {
        this.setTotal(total);
        return this;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public PaymentDetailType getType() {
        return this.type;
    }

    public PaymentDetails type(PaymentDetailType type) {
        this.setType(type);
        return this;
    }

    public void setType(PaymentDetailType type) {
        this.type = type;
    }

    public LocalDate getModifiedDate() {
        return this.modifiedDate;
    }

    public PaymentDetails modifiedDate(LocalDate modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public PaymentDetails createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public PaymentDetails modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public PaymentDetails createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Payment getPayment() {
        return this.payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public PaymentDetails payment(Payment payment) {
        this.setPayment(payment);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentDetails)) {
            return false;
        }
        return id != null && id.equals(((PaymentDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentDetails{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", unitPrice=" + getUnitPrice() +
            ", total=" + getTotal() +
            ", type='" + getType() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
