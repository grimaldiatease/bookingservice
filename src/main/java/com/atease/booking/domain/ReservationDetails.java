package com.atease.booking.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ReservationDetails.
 */
@Entity
@Table(name = "reservation_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReservationDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "check_in")
    private LocalDate checkIn;

    @Column(name = "check_out")
    private LocalDate checkOut;

    @Column(name = "adult")
    private Integer adult;

    @Column(name = "child")
    private Integer child;

    @Column(name = "infant")
    private Integer infant;

    @Column(name = "nights")
    private Integer nights;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ReservationDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCheckIn() {
        return this.checkIn;
    }

    public ReservationDetails checkIn(LocalDate checkIn) {
        this.setCheckIn(checkIn);
        return this;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return this.checkOut;
    }

    public ReservationDetails checkOut(LocalDate checkOut) {
        this.setCheckOut(checkOut);
        return this;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public Integer getAdult() {
        return this.adult;
    }

    public ReservationDetails adult(Integer adult) {
        this.setAdult(adult);
        return this;
    }

    public void setAdult(Integer adult) {
        this.adult = adult;
    }

    public Integer getChild() {
        return this.child;
    }

    public ReservationDetails child(Integer child) {
        this.setChild(child);
        return this;
    }

    public void setChild(Integer child) {
        this.child = child;
    }

    public Integer getInfant() {
        return this.infant;
    }

    public ReservationDetails infant(Integer infant) {
        this.setInfant(infant);
        return this;
    }

    public void setInfant(Integer infant) {
        this.infant = infant;
    }

    public Integer getNights() {
        return this.nights;
    }

    public ReservationDetails nights(Integer nights) {
        this.setNights(nights);
        return this;
    }

    public void setNights(Integer nights) {
        this.nights = nights;
    }

    public LocalDate getModifiedDate() {
        return this.modifiedDate;
    }

    public ReservationDetails modifiedDate(LocalDate modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public ReservationDetails createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public ReservationDetails modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public ReservationDetails createdBy(String createdBy) {
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

    public ReservationDetails reservation(Reservation reservation) {
        this.setReservation(reservation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReservationDetails)) {
            return false;
        }
        return id != null && id.equals(((ReservationDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReservationDetails{" +
            "id=" + getId() +
            ", checkIn='" + getCheckIn() + "'" +
            ", checkOut='" + getCheckOut() + "'" +
            ", adult=" + getAdult() +
            ", child=" + getChild() +
            ", infant=" + getInfant() +
            ", nights=" + getNights() +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
