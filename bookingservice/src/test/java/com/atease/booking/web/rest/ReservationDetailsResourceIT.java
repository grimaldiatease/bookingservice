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
    void createReservationDetails() throws Exception {
        int databaseSizeBeforeCreate = reservationDetailsRepository.findAll().size();
        // Create the ReservationDetails
        ReservationDetailsDTO reservationDetailsDTO = reservationDetailsMapper.toDto(reservationDetails);
        restReservationDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ReservationDetails in the database
        List<ReservationDetails> reservationDetailsList = reservationDetailsRepository.findAll();
        assertThat(reservationDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        ReservationDetails testReservationDetails = reservationDetailsList.get(reservationDetailsList.size() - 1);
        assertThat(testReservationDetails.getCheckIn()).isEqualTo(DEFAULT_CHECK_IN);
        assertThat(testReservationDetails.getCheckOut()).isEqualTo(DEFAULT_CHECK_OUT);
        assertThat(testReservationDetails.getAdult()).isEqualTo(DEFAULT_ADULT);
        assertThat(testReservationDetails.getChild()).isEqualTo(DEFAULT_CHILD);
        assertThat(testReservationDetails.getInfant()).isEqualTo(DEFAULT_INFANT);
        assertThat(testReservationDetails.getNights()).isEqualTo(DEFAULT_NIGHTS);
        assertThat(testReservationDetails.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testReservationDetails.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testReservationDetails.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testReservationDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    void createReservationDetailsWithExistingId() throws Exception {
        // Create the ReservationDetails with an existing ID
        reservationDetails.setId(1L);
        ReservationDetailsDTO reservationDetailsDTO = reservationDetailsMapper.toDto(reservationDetails);

        int databaseSizeBeforeCreate = reservationDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReservationDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReservationDetails in the database
        List<ReservationDetails> reservationDetailsList = reservationDetailsRepository.findAll();
        assertThat(reservationDetailsList).hasSize(databaseSizeBeforeCreate);
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

    @Test
    @Transactional
    void putExistingReservationDetails() throws Exception {
        // Initialize the database
        reservationDetailsRepository.saveAndFlush(reservationDetails);

        int databaseSizeBeforeUpdate = reservationDetailsRepository.findAll().size();

        // Update the reservationDetails
        ReservationDetails updatedReservationDetails = reservationDetailsRepository.findById(reservationDetails.getId()).get();
        // Disconnect from session so that the updates on updatedReservationDetails are not directly saved in db
        em.detach(updatedReservationDetails);
        updatedReservationDetails
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
        ReservationDetailsDTO reservationDetailsDTO = reservationDetailsMapper.toDto(updatedReservationDetails);

        restReservationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reservationDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the ReservationDetails in the database
        List<ReservationDetails> reservationDetailsList = reservationDetailsRepository.findAll();
        assertThat(reservationDetailsList).hasSize(databaseSizeBeforeUpdate);
        ReservationDetails testReservationDetails = reservationDetailsList.get(reservationDetailsList.size() - 1);
        assertThat(testReservationDetails.getCheckIn()).isEqualTo(UPDATED_CHECK_IN);
        assertThat(testReservationDetails.getCheckOut()).isEqualTo(UPDATED_CHECK_OUT);
        assertThat(testReservationDetails.getAdult()).isEqualTo(UPDATED_ADULT);
        assertThat(testReservationDetails.getChild()).isEqualTo(UPDATED_CHILD);
        assertThat(testReservationDetails.getInfant()).isEqualTo(UPDATED_INFANT);
        assertThat(testReservationDetails.getNights()).isEqualTo(UPDATED_NIGHTS);
        assertThat(testReservationDetails.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testReservationDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testReservationDetails.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testReservationDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void putNonExistingReservationDetails() throws Exception {
        int databaseSizeBeforeUpdate = reservationDetailsRepository.findAll().size();
        reservationDetails.setId(count.incrementAndGet());

        // Create the ReservationDetails
        ReservationDetailsDTO reservationDetailsDTO = reservationDetailsMapper.toDto(reservationDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReservationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reservationDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReservationDetails in the database
        List<ReservationDetails> reservationDetailsList = reservationDetailsRepository.findAll();
        assertThat(reservationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReservationDetails() throws Exception {
        int databaseSizeBeforeUpdate = reservationDetailsRepository.findAll().size();
        reservationDetails.setId(count.incrementAndGet());

        // Create the ReservationDetails
        ReservationDetailsDTO reservationDetailsDTO = reservationDetailsMapper.toDto(reservationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReservationDetails in the database
        List<ReservationDetails> reservationDetailsList = reservationDetailsRepository.findAll();
        assertThat(reservationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReservationDetails() throws Exception {
        int databaseSizeBeforeUpdate = reservationDetailsRepository.findAll().size();
        reservationDetails.setId(count.incrementAndGet());

        // Create the ReservationDetails
        ReservationDetailsDTO reservationDetailsDTO = reservationDetailsMapper.toDto(reservationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReservationDetails in the database
        List<ReservationDetails> reservationDetailsList = reservationDetailsRepository.findAll();
        assertThat(reservationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReservationDetailsWithPatch() throws Exception {
        // Initialize the database
        reservationDetailsRepository.saveAndFlush(reservationDetails);

        int databaseSizeBeforeUpdate = reservationDetailsRepository.findAll().size();

        // Update the reservationDetails using partial update
        ReservationDetails partialUpdatedReservationDetails = new ReservationDetails();
        partialUpdatedReservationDetails.setId(reservationDetails.getId());

        partialUpdatedReservationDetails
            .checkOut(UPDATED_CHECK_OUT)
            .child(UPDATED_CHILD)
            .nights(UPDATED_NIGHTS)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .createdDate(UPDATED_CREATED_DATE);

        restReservationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReservationDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReservationDetails))
            )
            .andExpect(status().isOk());

        // Validate the ReservationDetails in the database
        List<ReservationDetails> reservationDetailsList = reservationDetailsRepository.findAll();
        assertThat(reservationDetailsList).hasSize(databaseSizeBeforeUpdate);
        ReservationDetails testReservationDetails = reservationDetailsList.get(reservationDetailsList.size() - 1);
        assertThat(testReservationDetails.getCheckIn()).isEqualTo(DEFAULT_CHECK_IN);
        assertThat(testReservationDetails.getCheckOut()).isEqualTo(UPDATED_CHECK_OUT);
        assertThat(testReservationDetails.getAdult()).isEqualTo(DEFAULT_ADULT);
        assertThat(testReservationDetails.getChild()).isEqualTo(UPDATED_CHILD);
        assertThat(testReservationDetails.getInfant()).isEqualTo(DEFAULT_INFANT);
        assertThat(testReservationDetails.getNights()).isEqualTo(UPDATED_NIGHTS);
        assertThat(testReservationDetails.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testReservationDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testReservationDetails.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testReservationDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    void fullUpdateReservationDetailsWithPatch() throws Exception {
        // Initialize the database
        reservationDetailsRepository.saveAndFlush(reservationDetails);

        int databaseSizeBeforeUpdate = reservationDetailsRepository.findAll().size();

        // Update the reservationDetails using partial update
        ReservationDetails partialUpdatedReservationDetails = new ReservationDetails();
        partialUpdatedReservationDetails.setId(reservationDetails.getId());

        partialUpdatedReservationDetails
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

        restReservationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReservationDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReservationDetails))
            )
            .andExpect(status().isOk());

        // Validate the ReservationDetails in the database
        List<ReservationDetails> reservationDetailsList = reservationDetailsRepository.findAll();
        assertThat(reservationDetailsList).hasSize(databaseSizeBeforeUpdate);
        ReservationDetails testReservationDetails = reservationDetailsList.get(reservationDetailsList.size() - 1);
        assertThat(testReservationDetails.getCheckIn()).isEqualTo(UPDATED_CHECK_IN);
        assertThat(testReservationDetails.getCheckOut()).isEqualTo(UPDATED_CHECK_OUT);
        assertThat(testReservationDetails.getAdult()).isEqualTo(UPDATED_ADULT);
        assertThat(testReservationDetails.getChild()).isEqualTo(UPDATED_CHILD);
        assertThat(testReservationDetails.getInfant()).isEqualTo(UPDATED_INFANT);
        assertThat(testReservationDetails.getNights()).isEqualTo(UPDATED_NIGHTS);
        assertThat(testReservationDetails.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testReservationDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testReservationDetails.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testReservationDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingReservationDetails() throws Exception {
        int databaseSizeBeforeUpdate = reservationDetailsRepository.findAll().size();
        reservationDetails.setId(count.incrementAndGet());

        // Create the ReservationDetails
        ReservationDetailsDTO reservationDetailsDTO = reservationDetailsMapper.toDto(reservationDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReservationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reservationDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reservationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReservationDetails in the database
        List<ReservationDetails> reservationDetailsList = reservationDetailsRepository.findAll();
        assertThat(reservationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReservationDetails() throws Exception {
        int databaseSizeBeforeUpdate = reservationDetailsRepository.findAll().size();
        reservationDetails.setId(count.incrementAndGet());

        // Create the ReservationDetails
        ReservationDetailsDTO reservationDetailsDTO = reservationDetailsMapper.toDto(reservationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reservationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReservationDetails in the database
        List<ReservationDetails> reservationDetailsList = reservationDetailsRepository.findAll();
        assertThat(reservationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReservationDetails() throws Exception {
        int databaseSizeBeforeUpdate = reservationDetailsRepository.findAll().size();
        reservationDetails.setId(count.incrementAndGet());

        // Create the ReservationDetails
        ReservationDetailsDTO reservationDetailsDTO = reservationDetailsMapper.toDto(reservationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reservationDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReservationDetails in the database
        List<ReservationDetails> reservationDetailsList = reservationDetailsRepository.findAll();
        assertThat(reservationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReservationDetails() throws Exception {
        // Initialize the database
        reservationDetailsRepository.saveAndFlush(reservationDetails);

        int databaseSizeBeforeDelete = reservationDetailsRepository.findAll().size();

        // Delete the reservationDetails
        restReservationDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, reservationDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReservationDetails> reservationDetailsList = reservationDetailsRepository.findAll();
        assertThat(reservationDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
