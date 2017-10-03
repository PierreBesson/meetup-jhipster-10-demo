package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Tour.
 */
@Entity
@Table(name = "tour")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tour implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "number_of_days")
    private Integer numberOfDays;

    @Column(name = "number_of_nights")
    private Integer numberOfNights;

    @Column(name = "description")
    private String description;

    @Column(name = "time_of_departure")
    private ZonedDateTime timeOfDeparture;

    @Column(name = "time_of_return")
    private ZonedDateTime timeOfReturn;

    @Column(name = "price")
    private Integer price;

    @Column(name = "capacity")
    private Integer capacity;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Tour code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Tour name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public Tour numberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
        return this;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public Integer getNumberOfNights() {
        return numberOfNights;
    }

    public Tour numberOfNights(Integer numberOfNights) {
        this.numberOfNights = numberOfNights;
        return this;
    }

    public void setNumberOfNights(Integer numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    public String getDescription() {
        return description;
    }

    public Tour description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getTimeOfDeparture() {
        return timeOfDeparture;
    }

    public Tour timeOfDeparture(ZonedDateTime timeOfDeparture) {
        this.timeOfDeparture = timeOfDeparture;
        return this;
    }

    public void setTimeOfDeparture(ZonedDateTime timeOfDeparture) {
        this.timeOfDeparture = timeOfDeparture;
    }

    public ZonedDateTime getTimeOfReturn() {
        return timeOfReturn;
    }

    public Tour timeOfReturn(ZonedDateTime timeOfReturn) {
        this.timeOfReturn = timeOfReturn;
        return this;
    }

    public void setTimeOfReturn(ZonedDateTime timeOfReturn) {
        this.timeOfReturn = timeOfReturn;
    }

    public Integer getPrice() {
        return price;
    }

    public Tour price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Tour capacity(Integer capacity) {
        this.capacity = capacity;
        return this;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tour tour = (Tour) o;
        if (tour.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tour.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tour{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", numberOfDays='" + getNumberOfDays() + "'" +
            ", numberOfNights='" + getNumberOfNights() + "'" +
            ", description='" + getDescription() + "'" +
            ", timeOfDeparture='" + getTimeOfDeparture() + "'" +
            ", timeOfReturn='" + getTimeOfReturn() + "'" +
            ", price='" + getPrice() + "'" +
            ", capacity='" + getCapacity() + "'" +
            "}";
    }
}
