package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CatalogApp;

import com.mycompany.myapp.domain.Tour;
import com.mycompany.myapp.repository.TourRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TourResource REST controller.
 *
 * @see TourResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatalogApp.class)
public class TourResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER_OF_DAYS = 1;
    private static final Integer UPDATED_NUMBER_OF_DAYS = 2;

    private static final Integer DEFAULT_NUMBER_OF_NIGHTS = 1;
    private static final Integer UPDATED_NUMBER_OF_NIGHTS = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIME_OF_DEPARTURE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME_OF_DEPARTURE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_TIME_OF_RETURN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME_OF_RETURN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    private static final Integer DEFAULT_CAPACITY = 1;
    private static final Integer UPDATED_CAPACITY = 2;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTourMockMvc;

    private Tour tour;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TourResource tourResource = new TourResource(tourRepository);
        this.restTourMockMvc = MockMvcBuilders.standaloneSetup(tourResource)
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
    public static Tour createEntity(EntityManager em) {
        Tour tour = new Tour()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .numberOfDays(DEFAULT_NUMBER_OF_DAYS)
            .numberOfNights(DEFAULT_NUMBER_OF_NIGHTS)
            .description(DEFAULT_DESCRIPTION)
            .timeOfDeparture(DEFAULT_TIME_OF_DEPARTURE)
            .timeOfReturn(DEFAULT_TIME_OF_RETURN)
            .price(DEFAULT_PRICE)
            .capacity(DEFAULT_CAPACITY);
        return tour;
    }

    @Before
    public void initTest() {
        tour = createEntity(em);
    }

    @Test
    @Transactional
    public void createTour() throws Exception {
        int databaseSizeBeforeCreate = tourRepository.findAll().size();

        // Create the Tour
        restTourMockMvc.perform(post("/api/tours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tour)))
            .andExpect(status().isCreated());

        // Validate the Tour in the database
        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeCreate + 1);
        Tour testTour = tourList.get(tourList.size() - 1);
        assertThat(testTour.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTour.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTour.getNumberOfDays()).isEqualTo(DEFAULT_NUMBER_OF_DAYS);
        assertThat(testTour.getNumberOfNights()).isEqualTo(DEFAULT_NUMBER_OF_NIGHTS);
        assertThat(testTour.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTour.getTimeOfDeparture()).isEqualTo(DEFAULT_TIME_OF_DEPARTURE);
        assertThat(testTour.getTimeOfReturn()).isEqualTo(DEFAULT_TIME_OF_RETURN);
        assertThat(testTour.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testTour.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
    }

    @Test
    @Transactional
    public void createTourWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tourRepository.findAll().size();

        // Create the Tour with an existing ID
        tour.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTourMockMvc.perform(post("/api/tours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tour)))
            .andExpect(status().isBadRequest());

        // Validate the Tour in the database
        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTours() throws Exception {
        // Initialize the database
        tourRepository.saveAndFlush(tour);

        // Get all the tourList
        restTourMockMvc.perform(get("/api/tours?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tour.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].numberOfDays").value(hasItem(DEFAULT_NUMBER_OF_DAYS)))
            .andExpect(jsonPath("$.[*].numberOfNights").value(hasItem(DEFAULT_NUMBER_OF_NIGHTS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].timeOfDeparture").value(hasItem(sameInstant(DEFAULT_TIME_OF_DEPARTURE))))
            .andExpect(jsonPath("$.[*].timeOfReturn").value(hasItem(sameInstant(DEFAULT_TIME_OF_RETURN))))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY)));
    }

    @Test
    @Transactional
    public void getTour() throws Exception {
        // Initialize the database
        tourRepository.saveAndFlush(tour);

        // Get the tour
        restTourMockMvc.perform(get("/api/tours/{id}", tour.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tour.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.numberOfDays").value(DEFAULT_NUMBER_OF_DAYS))
            .andExpect(jsonPath("$.numberOfNights").value(DEFAULT_NUMBER_OF_NIGHTS))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.timeOfDeparture").value(sameInstant(DEFAULT_TIME_OF_DEPARTURE)))
            .andExpect(jsonPath("$.timeOfReturn").value(sameInstant(DEFAULT_TIME_OF_RETURN)))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.capacity").value(DEFAULT_CAPACITY));
    }

    @Test
    @Transactional
    public void getNonExistingTour() throws Exception {
        // Get the tour
        restTourMockMvc.perform(get("/api/tours/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTour() throws Exception {
        // Initialize the database
        tourRepository.saveAndFlush(tour);
        int databaseSizeBeforeUpdate = tourRepository.findAll().size();

        // Update the tour
        Tour updatedTour = tourRepository.findOne(tour.getId());
        updatedTour
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .numberOfDays(UPDATED_NUMBER_OF_DAYS)
            .numberOfNights(UPDATED_NUMBER_OF_NIGHTS)
            .description(UPDATED_DESCRIPTION)
            .timeOfDeparture(UPDATED_TIME_OF_DEPARTURE)
            .timeOfReturn(UPDATED_TIME_OF_RETURN)
            .price(UPDATED_PRICE)
            .capacity(UPDATED_CAPACITY);

        restTourMockMvc.perform(put("/api/tours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTour)))
            .andExpect(status().isOk());

        // Validate the Tour in the database
        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeUpdate);
        Tour testTour = tourList.get(tourList.size() - 1);
        assertThat(testTour.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTour.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTour.getNumberOfDays()).isEqualTo(UPDATED_NUMBER_OF_DAYS);
        assertThat(testTour.getNumberOfNights()).isEqualTo(UPDATED_NUMBER_OF_NIGHTS);
        assertThat(testTour.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTour.getTimeOfDeparture()).isEqualTo(UPDATED_TIME_OF_DEPARTURE);
        assertThat(testTour.getTimeOfReturn()).isEqualTo(UPDATED_TIME_OF_RETURN);
        assertThat(testTour.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testTour.getCapacity()).isEqualTo(UPDATED_CAPACITY);
    }

    @Test
    @Transactional
    public void updateNonExistingTour() throws Exception {
        int databaseSizeBeforeUpdate = tourRepository.findAll().size();

        // Create the Tour

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTourMockMvc.perform(put("/api/tours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tour)))
            .andExpect(status().isCreated());

        // Validate the Tour in the database
        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTour() throws Exception {
        // Initialize the database
        tourRepository.saveAndFlush(tour);
        int databaseSizeBeforeDelete = tourRepository.findAll().size();

        // Get the tour
        restTourMockMvc.perform(delete("/api/tours/{id}", tour.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tour.class);
        Tour tour1 = new Tour();
        tour1.setId(1L);
        Tour tour2 = new Tour();
        tour2.setId(tour1.getId());
        assertThat(tour1).isEqualTo(tour2);
        tour2.setId(2L);
        assertThat(tour1).isNotEqualTo(tour2);
        tour1.setId(null);
        assertThat(tour1).isNotEqualTo(tour2);
    }
}
