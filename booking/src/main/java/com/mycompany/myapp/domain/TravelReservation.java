package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TravelReservation.
 */
@Entity
@Table(name = "travel_reservation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TravelReservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "tour_code")
    private String tourCode;

    @Column(name = "number_of_persons")
    private Integer numberOfPersons;

    @Column(name = "need_visa")
    private Boolean needVisa;

    @Column(name = "client_confirmed")
    private Boolean clientConfirmed;

    @Column(name = "provider_confirmed")
    private Boolean providerConfirmed;

    @Column(name = "agency_confirmed")
    private Boolean agencyConfirmed;

    @Column(name = "notes")
    private String notes;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTourCode() {
        return tourCode;
    }

    public TravelReservation tourCode(String tourCode) {
        this.tourCode = tourCode;
        return this;
    }

    public void setTourCode(String tourCode) {
        this.tourCode = tourCode;
    }

    public Integer getNumberOfPersons() {
        return numberOfPersons;
    }

    public TravelReservation numberOfPersons(Integer numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
        return this;
    }

    public void setNumberOfPersons(Integer numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public Boolean isNeedVisa() {
        return needVisa;
    }

    public TravelReservation needVisa(Boolean needVisa) {
        this.needVisa = needVisa;
        return this;
    }

    public void setNeedVisa(Boolean needVisa) {
        this.needVisa = needVisa;
    }

    public Boolean isClientConfirmed() {
        return clientConfirmed;
    }

    public TravelReservation clientConfirmed(Boolean clientConfirmed) {
        this.clientConfirmed = clientConfirmed;
        return this;
    }

    public void setClientConfirmed(Boolean clientConfirmed) {
        this.clientConfirmed = clientConfirmed;
    }

    public Boolean isProviderConfirmed() {
        return providerConfirmed;
    }

    public TravelReservation providerConfirmed(Boolean providerConfirmed) {
        this.providerConfirmed = providerConfirmed;
        return this;
    }

    public void setProviderConfirmed(Boolean providerConfirmed) {
        this.providerConfirmed = providerConfirmed;
    }

    public Boolean isAgencyConfirmed() {
        return agencyConfirmed;
    }

    public TravelReservation agencyConfirmed(Boolean agencyConfirmed) {
        this.agencyConfirmed = agencyConfirmed;
        return this;
    }

    public void setAgencyConfirmed(Boolean agencyConfirmed) {
        this.agencyConfirmed = agencyConfirmed;
    }

    public String getNotes() {
        return notes;
    }

    public TravelReservation notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TravelReservation travelReservation = (TravelReservation) o;
        if (travelReservation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), travelReservation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TravelReservation{" +
            "id=" + getId() +
            ", tourCode='" + getTourCode() + "'" +
            ", numberOfPersons='" + getNumberOfPersons() + "'" +
            ", needVisa='" + isNeedVisa() + "'" +
            ", clientConfirmed='" + isClientConfirmed() + "'" +
            ", providerConfirmed='" + isProviderConfirmed() + "'" +
            ", agencyConfirmed='" + isAgencyConfirmed() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
