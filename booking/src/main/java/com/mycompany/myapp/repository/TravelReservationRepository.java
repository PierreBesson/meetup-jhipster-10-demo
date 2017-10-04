package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TravelReservation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TravelReservation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TravelReservationRepository extends JpaRepository<TravelReservation, Long> {

}
