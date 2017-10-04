package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.TravelReservation;

import com.mycompany.myapp.repository.TravelReservationRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TravelReservation.
 */
@RestController
@RequestMapping("/api")
public class TravelReservationResource {

    private final Logger log = LoggerFactory.getLogger(TravelReservationResource.class);

    private static final String ENTITY_NAME = "travelReservation";

    private final TravelReservationRepository travelReservationRepository;

    public TravelReservationResource(TravelReservationRepository travelReservationRepository) {
        this.travelReservationRepository = travelReservationRepository;
    }

    /**
     * POST  /travel-reservations : Create a new travelReservation.
     *
     * @param travelReservation the travelReservation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new travelReservation, or with status 400 (Bad Request) if the travelReservation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/travel-reservations")
    @Timed
    public ResponseEntity<TravelReservation> createTravelReservation(@RequestBody TravelReservation travelReservation) throws URISyntaxException {
        log.debug("REST request to save TravelReservation : {}", travelReservation);
        if (travelReservation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new travelReservation cannot already have an ID")).body(null);
        }
        TravelReservation result = travelReservationRepository.save(travelReservation);
        return ResponseEntity.created(new URI("/api/travel-reservations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /travel-reservations : Updates an existing travelReservation.
     *
     * @param travelReservation the travelReservation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated travelReservation,
     * or with status 400 (Bad Request) if the travelReservation is not valid,
     * or with status 500 (Internal Server Error) if the travelReservation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/travel-reservations")
    @Timed
    public ResponseEntity<TravelReservation> updateTravelReservation(@RequestBody TravelReservation travelReservation) throws URISyntaxException {
        log.debug("REST request to update TravelReservation : {}", travelReservation);
        if (travelReservation.getId() == null) {
            return createTravelReservation(travelReservation);
        }
        TravelReservation result = travelReservationRepository.save(travelReservation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, travelReservation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /travel-reservations : get all the travelReservations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of travelReservations in body
     */
    @GetMapping("/travel-reservations")
    @Timed
    public List<TravelReservation> getAllTravelReservations() {
        log.debug("REST request to get all TravelReservations");
        return travelReservationRepository.findAll();
        }

    /**
     * GET  /travel-reservations/:id : get the "id" travelReservation.
     *
     * @param id the id of the travelReservation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the travelReservation, or with status 404 (Not Found)
     */
    @GetMapping("/travel-reservations/{id}")
    @Timed
    public ResponseEntity<TravelReservation> getTravelReservation(@PathVariable Long id) {
        log.debug("REST request to get TravelReservation : {}", id);
        TravelReservation travelReservation = travelReservationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(travelReservation));
    }

    /**
     * DELETE  /travel-reservations/:id : delete the "id" travelReservation.
     *
     * @param id the id of the travelReservation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/travel-reservations/{id}")
    @Timed
    public ResponseEntity<Void> deleteTravelReservation(@PathVariable Long id) {
        log.debug("REST request to delete TravelReservation : {}", id);
        travelReservationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
