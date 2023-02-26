package com.atease.booking.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atease.booking.IntegrationTest;
import com.atease.booking.domain.ReservationDetails;
import com.atease.booking.repository.ReservationDetailsRepository;
import com.atease.booking.service.dto.ReservationDetailsDTO;
import com.atease.booking.service.mapper.ReservationDetailsMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ReservationDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReservationDetailsResourceIT {

    private static final LocalDate DEFAULT_CHECK_IN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CHECK_IN = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CHECK_OUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CHECK_OUT = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_ADULT = 1;
    private static final Integer UPDATED_ADULT = 2;

    private static final Integer DEFAULT_CHILD = 1;
    private static final Integer UPDATED_CHILD = 2;

    private static final Integer DEFAULT_INFANT = 1;
    private static final Integer UPDATED_INFANT = 2;

    private static final Integer DEFAULT_NIGHTS = 1;
    private static final Integer UPDATED_NIGHTS = 2;

    private static final LocalDate DEFAULT_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/reservation-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReservationDetailsRepository reservationDetailsRepository;

    @Autowired
    private ReservationDetailsMapper reservationDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReservationDetailsMockMvc;

    private ReservationDetails reservationDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReservationDetails createEntity(EntityManager em) {
        ReservationDetails reservationDetails = new ReservationDetails()
            .checkIn(DEFAULT_CHECK_IN)
            .checkOut(DEFAULT_CHECK_OUT)
            .adult(DEFAULT_ADULT)
            .child(DEFAULT_CHILD)
            .infant(DEFAULT_INFANT)
            .nights(DEFAULT_NIGHTS)
            .modifiedDate(DEFAULT_MODIFIED_DATE)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY);
        return reservationDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReservationDetails createUpdatedEntity(EntityManager em) {
        ReservationDetails reservationDetails = new ReservationDetails()
            .checkIn(UPDATED_CHECK_IN)
            .checkOut(UPDATED_CHECK_OUT)
            .adult(UPDATED_ADULT)
            .child(UPDATED_CHILD)
            .infant(UPDATED_INFANT)
            .nights(UPDATED_NIGHTS)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY);
        return reservationDetails;
    }

    @BeforeEach
    public void initTest() {
        reservationDetails = createEntity(em);
    }

    @Test
    @Transactional
    void getAllReservationDetails() throws Exception {
        // Initialize the database
        reservationDetailsRepository.saveAndFlush(reservationDetails);

        // Get all the reservationDetailsList
        restReservationDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reservationDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].checkIn").value(hasItem(DEFAULT_CHECK_IN.toString())))
            .andExpect(jsonPath("$.[*].checkOut").value(hasItem(DEFAULT_CHECK_OUT.toString())))
            .andExpect(jsonPath("$.[*].adult").value(hasItem(DEFAULT_ADULT)))
            .andExpect(jsonPath("$.[*].child").value(hasItem(DEFAULT_CHILD)))
            .andExpect(jsonPath("$.[*].infant").value(hasItem(DEFAULT_INFANT)))
            .andExpect(jsonPath("$.[*].nights").value(hasItem(DEFAULT_NIGHTS)))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)));
    }

    @Test
    @Transactional
    void getReservationDetails() throws Exception {
        // Initialize the database
        reservationDetailsRepository.saveAndFlush(reservationDetails);

        // Get the reservationDetails
        restReservationDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, reservationDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reservationDetails.getId().intValue()))
            .andExpect(jsonPath("$.checkIn").value(DEFAULT_CHECK_IN.toString()))
            .andExpect(jsonPath("$.checkOut").value(DEFAULT_CHECK_OUT.toString()))
            .andExpect(jsonPath("$.adult").value(DEFAULT_ADULT))
            .andExpect(jsonPath("$.child").value(DEFAULT_CHILD))
            .andExpect(jsonPath("$.infant").value(DEFAULT_INFANT))
            .andExpect(jsonPath("$.nights").value(DEFAULT_NIGHTS))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingReservationDetails() throws Exception {
        // Get the reservationDetails
        restReservationDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }
}
