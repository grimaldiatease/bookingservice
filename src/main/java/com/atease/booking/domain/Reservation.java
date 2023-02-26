package com.atease.booking.domain;

import com.atease.booking.domain.enumeration.ReservationStatus;
import com.atease.booking.domain.enumeration.ReservationType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Reservation.
 */
@Entity
@Table(name = "reservation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ReservationType type;

    @Column(name = "property_id")
    private Long propertyId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReservationStatus status;

    @Column(name = "modified_date")
    private LocalDate modifiedDate;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "created_by")
    private String createdBy;

    @ManyToOne
    @JsonIgnoreProperties(value = { "reservations" }, allowSetters = true)
    private Guest guest;

    @JsonIgnoreProperties(value = { "reservation", "paymentDetails", "paymentTypes" }, allowSetters = true)
    @OneToOne(mappedBy = "reservation")
    private Payment payment;

    @JsonIgnoreProperties(value = { "reservation" }, allowSetters = true)
    @OneToOne(mappedBy = "reservation")
    private ReservationDetails reservationDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Reservation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReservationType getType() {
        return this.type;
    }

    public Reservation type(ReservationType type) {
        this.setType(type);
        return this;
    }

    public void setType(ReservationType type) {
        this.type = type;
    }

    public Long getPropertyId() {
        return this.propertyId;
    }

    public Reservation propertyId(Long propertyId) {
        this.setPropertyId(propertyId);
        return this;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public ReservationStatus getStatus() {
        return this.status;
    }

    public Reservation status(ReservationStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public LocalDate getModifiedDate() {
        return this.modifiedDate;
    }

    public Reservation modifiedDate(LocalDate modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Reservation createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public Reservation modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Reservation createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Guest getGuest() {
        return this.guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Reservation guest(Guest guest) {
        this.setGuest(guest);
        return this;
    }

    public Payment getPayment() {
        return this.payment;
    }

    public void setPayment(Payment payment) {
        if (this.payment != null) {
            this.payment.setReservation(null);
        }
        if (payment != null) {
            payment.setReservation(this);
        }
        this.payment = payment;
    }

    public Reservation payment(Payment payment) {
        this.setPayment(payment);
        return this;
    }

    public ReservationDetails getReservationDetails() {
        return this.reservationDetails;
    }

    public void setReservationDetails(ReservationDetails reservationDetails) {
        if (this.reservationDetails != null) {
            this.reservationDetails.setReservation(null);
        }
        if (reservationDetails != null) {
            reservationDetails.setReservation(this);
        }
        this.reservationDetails = reservationDetails;
    }

    public Reservation reservationDetails(ReservationDetails reservationDetails) {
        this.setReservationDetails(reservationDetails);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reservation)) {
            return false;
        }
        return id != null && id.equals(((Reservation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reservation{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", propertyId=" + getPropertyId() +
            ", status='" + getStatus() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
