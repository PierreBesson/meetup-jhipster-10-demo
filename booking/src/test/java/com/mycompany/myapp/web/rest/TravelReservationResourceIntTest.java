package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.BookingApp;

import com.mycompany.myapp.domain.TravelReservation;
import com.mycompany.myapp.repository.TravelReservationRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TravelReservationResource REST controller.
 *
 * @see TravelReservationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingApp.class)
public class TravelReservationResourceIntTest {

    private static final String DEFAULT_TOUR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TOUR_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER_OF_PERSONS = 1;
    private static final Integer UPDATED_NUMBER_OF_PERSONS = 2;

    private static final Boolean DEFAULT_NEED_VISA = false;
    private static final Boolean UPDATED_NEED_VISA = true;

    private static final Boolean DEFAULT_CLIENT_CONFIRMED = false;
    private static final Boolean UPDATED_CLIENT_CONFIRMED = true;

    private static final Boolean DEFAULT_PROVIDER_CONFIRMED = false;
    private static final Boolean UPDATED_PROVIDER_CONFIRMED = true;

    private static final Boolean DEFAULT_AGENCY_CONFIRMED = false;
    private static final Boolean UPDATED_AGENCY_CONFIRMED = true;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    @Autowired
    private TravelReservationRepository travelReservationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTravelReservationMockMvc;

    private TravelReservation travelReservation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TravelReservationResource travelReservationResource = new TravelReservationResource(travelReservationRepository);
        this.restTravelReservationMockMvc = MockMvcBuilders.standaloneSetup(travelReservationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TravelReservation createEntity(EntityManager em) {
        TravelReservation travelReservation = new TravelReservation()
            .tourCode(DEFAULT_TOUR_CODE)
            .numberOfPersons(DEFAULT_NUMBER_OF_PERSONS)
            .needVisa(DEFAULT_NEED_VISA)
            .clientConfirmed(DEFAULT_CLIENT_CONFIRMED)
            .providerConfirmed(DEFAULT_PROVIDER_CONFIRMED)
            .agencyConfirmed(DEFAULT_AGENCY_CONFIRMED)
            .notes(DEFAULT_NOTES);
        return travelReservation;
    }

    @Before
    public void initTest() {
        travelReservation = createEntity(em);
    }

    @Test
    @Transactional
    public void createTravelReservation() throws Exception {
        int databaseSizeBeforeCreate = travelReservationRepository.findAll().size();

        // Create the TravelReservation
        restTravelReservationMockMvc.perform(post("/api/travel-reservations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(travelReservation)))
            .andExpect(status().isCreated());

        // Validate the TravelReservation in the database
        List<TravelReservation> travelReservationList = travelReservationRepository.findAll();
        assertThat(travelReservationList).hasSize(databaseSizeBeforeCreate + 1);
        TravelReservation testTravelReservation = travelReservationList.get(travelReservationList.size() - 1);
        assertThat(testTravelReservation.getTourCode()).isEqualTo(DEFAULT_TOUR_CODE);
        assertThat(testTravelReservation.getNumberOfPersons()).isEqualTo(DEFAULT_NUMBER_OF_PERSONS);
        assertThat(testTravelReservation.isNeedVisa()).isEqualTo(DEFAULT_NEED_VISA);
        assertThat(testTravelReservation.isClientConfirmed()).isEqualTo(DEFAULT_CLIENT_CONFIRMED);
        assertThat(testTravelReservation.isProviderConfirmed()).isEqualTo(DEFAULT_PROVIDER_CONFIRMED);
        assertThat(testTravelReservation.isAgencyConfirmed()).isEqualTo(DEFAULT_AGENCY_CONFIRMED);
        assertThat(testTravelReservation.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    public void createTravelReservationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = travelReservationRepository.findAll().size();

        // Create the TravelReservation with an existing ID
        travelReservation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTravelReservationMockMvc.perform(post("/api/travel-reservations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(travelReservation)))
            .andExpect(status().isBadRequest());

        // Validate the TravelReservation in the database
        List<TravelReservation> travelReservationList = travelReservationRepository.findAll();
        assertThat(travelReservationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTravelReservations() throws Exception {
        // Initialize the database
        travelReservationRepository.saveAndFlush(travelReservation);

        // Get all the travelReservationList
        restTravelReservationMockMvc.perform(get("/api/travel-reservations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(travelReservation.getId().intValue())))
            .andExpect(jsonPath("$.[*].tourCode").value(hasItem(DEFAULT_TOUR_CODE.toString())))
            .andExpect(jsonPath("$.[*].numberOfPersons").value(hasItem(DEFAULT_NUMBER_OF_PERSONS)))
            .andExpect(jsonPath("$.[*].needVisa").value(hasItem(DEFAULT_NEED_VISA.booleanValue())))
            .andExpect(jsonPath("$.[*].clientConfirmed").value(hasItem(DEFAULT_CLIENT_CONFIRMED.booleanValue())))
            .andExpect(jsonPath("$.[*].providerConfirmed").value(hasItem(DEFAULT_PROVIDER_CONFIRMED.booleanValue())))
            .andExpect(jsonPath("$.[*].agencyConfirmed").value(hasItem(DEFAULT_AGENCY_CONFIRMED.booleanValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())));
    }

    @Test
    @Transactional
    public void getTravelReservation() throws Exception {
        // Initialize the database
        travelReservationRepository.saveAndFlush(travelReservation);

        // Get the travelReservation
        restTravelReservationMockMvc.perform(get("/api/travel-reservations/{id}", travelReservation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(travelReservation.getId().intValue()))
            .andExpect(jsonPath("$.tourCode").value(DEFAULT_TOUR_CODE.toString()))
            .andExpect(jsonPath("$.numberOfPersons").value(DEFAULT_NUMBER_OF_PERSONS))
            .andExpect(jsonPath("$.needVisa").value(DEFAULT_NEED_VISA.booleanValue()))
            .andExpect(jsonPath("$.clientConfirmed").value(DEFAULT_CLIENT_CONFIRMED.booleanValue()))
            .andExpect(jsonPath("$.providerConfirmed").value(DEFAULT_PROVIDER_CONFIRMED.booleanValue()))
            .andExpect(jsonPath("$.agencyConfirmed").value(DEFAULT_AGENCY_CONFIRMED.booleanValue()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTravelReservation() throws Exception {
        // Get the travelReservation
        restTravelReservationMockMvc.perform(get("/api/travel-reservations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTravelReservation() throws Exception {
        // Initialize the database
        travelReservationRepository.saveAndFlush(travelReservation);
        int databaseSizeBeforeUpdate = travelReservationRepository.findAll().size();

        // Update the travelReservation
        TravelReservation updatedTravelReservation = travelReservationRepository.findOne(travelReservation.getId());
        updatedTravelReservation
            .tourCode(UPDATED_TOUR_CODE)
            .numberOfPersons(UPDATED_NUMBER_OF_PERSONS)
            .needVisa(UPDATED_NEED_VISA)
            .clientConfirmed(UPDATED_CLIENT_CONFIRMED)
            .providerConfirmed(UPDATED_PROVIDER_CONFIRMED)
            .agencyConfirmed(UPDATED_AGENCY_CONFIRMED)
            .notes(UPDATED_NOTES);

        restTravelReservationMockMvc.perform(put("/api/travel-reservations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTravelReservation)))
            .andExpect(status().isOk());

        // Validate the TravelReservation in the database
        List<TravelReservation> travelReservationList = travelReservationRepository.findAll();
        assertThat(travelReservationList).hasSize(databaseSizeBeforeUpdate);
        TravelReservation testTravelReservation = travelReservationList.get(travelReservationList.size() - 1);
        assertThat(testTravelReservation.getTourCode()).isEqualTo(UPDATED_TOUR_CODE);
        assertThat(testTravelReservation.getNumberOfPersons()).isEqualTo(UPDATED_NUMBER_OF_PERSONS);
        assertThat(testTravelReservation.isNeedVisa()).isEqualTo(UPDATED_NEED_VISA);
        assertThat(testTravelReservation.isClientConfirmed()).isEqualTo(UPDATED_CLIENT_CONFIRMED);
        assertThat(testTravelReservation.isProviderConfirmed()).isEqualTo(UPDATED_PROVIDER_CONFIRMED);
        assertThat(testTravelReservation.isAgencyConfirmed()).isEqualTo(UPDATED_AGENCY_CONFIRMED);
        assertThat(testTravelReservation.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void updateNonExistingTravelReservation() throws Exception {
        int databaseSizeBeforeUpdate = travelReservationRepository.findAll().size();

        // Create the TravelReservation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTravelReservationMockMvc.perform(put("/api/travel-reservations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(travelReservation)))
            .andExpect(status().isCreated());

        // Validate the TravelReservation in the database
        List<TravelReservation> travelReservationList = travelReservationRepository.findAll();
        assertThat(travelReservationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTravelReservation() throws Exception {
        // Initialize the database
        travelReservationRepository.saveAndFlush(travelReservation);
        int databaseSizeBeforeDelete = travelReservationRepository.findAll().size();

        // Get the travelReservation
        restTravelReservationMockMvc.perform(delete("/api/travel-reservations/{id}", travelReservation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TravelReservation> travelReservationList = travelReservationRepository.findAll();
        assertThat(travelReservationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TravelReservation.class);
        TravelReservation travelReservation1 = new TravelReservation();
        travelReservation1.setId(1L);
        TravelReservation travelReservation2 = new TravelReservation();
        travelReservation2.setId(travelReservation1.getId());
        assertThat(travelReservation1).isEqualTo(travelReservation2);
        travelReservation2.setId(2L);
        assertThat(travelReservation1).isNotEqualTo(travelReservation2);
        travelReservation1.setId(null);
        assertThat(travelReservation1).isNotEqualTo(travelReservation2);
    }
}
