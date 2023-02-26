package com.atease.booking.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "modified_date")
    private LocalDate modifiedDate;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "created_by")
    private String createdBy;

    @JsonIgnoreProperties(value = { "guest", "payment", "reservationDetails" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Reservation reservation;

    @OneToMany(mappedBy = "payment")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "payment" }, allowSetters = true)
    private Set<PaymentDetails> paymentDetails = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Payment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getModifiedDate() {
        return this.modifiedDate;
    }

    public Payment modifiedDate(LocalDate modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Payment createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public Payment modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Payment createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Reservation getReservation() {
        return this.reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Payment reservation(Reservation reservation) {
        this.setReservation(reservation);
        return this;
    }

    public Set<PaymentDetails> getPaymentDetails() {
        return this.paymentDetails;
    }

    public void setPaymentDetails(Set<PaymentDetails> paymentDetails) {
        if (this.paymentDetails != null) {
            this.paymentDetails.forEach(i -> i.setPayment(null));
        }
        if (paymentDetails != null) {
            paymentDetails.forEach(i -> i.setPayment(this));
        }
        this.paymentDetails = paymentDetails;
    }

    public Payment paymentDetails(Set<PaymentDetails> paymentDetails) {
        this.setPaymentDetails(paymentDetails);
        return this;
    }

    public Payment addPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails.add(paymentDetails);
        paymentDetails.setPayment(this);
        return this;
    }

    public Payment removePaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails.remove(paymentDetails);
        paymentDetails.setPayment(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payment)) {
            return false;
        }
        return id != null && id.equals(((Payment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payment{" +
            "id=" + getId() +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
