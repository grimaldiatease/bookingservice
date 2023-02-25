package com.atease.booking.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atease.booking.IntegrationTest;
import com.atease.booking.domain.Guest;
import com.atease.booking.repository.GuestRepository;
import com.atease.booking.service.dto.GuestDTO;
import com.atease.booking.service.mapper.GuestMapper;
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
 * Integration tests for the {@link GuestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GuestResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP = "AAAAAAAAAA";
    private static final String UPDATED_ZIP = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/guests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private GuestMapper guestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGuestMockMvc;

    private Guest guest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Guest createEntity(EntityManager em) {
        Guest guest = new Guest()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
            .address1(DEFAULT_ADDRESS_1)
            .address2(DEFAULT_ADDRESS_2)
            .country(DEFAULT_COUNTRY)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .zip(DEFAULT_ZIP)
            .modifiedDate(DEFAULT_MODIFIED_DATE)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY);
        return guest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Guest createUpdatedEntity(EntityManager em) {
        Guest guest = new Guest()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .zip(UPDATED_ZIP)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY);
        return guest;
    }

    @BeforeEach
    public void initTest() {
        guest = createEntity(em);
    }

    @Test
    @Transactional
    void createGuest() throws Exception {
        int databaseSizeBeforeCreate = guestRepository.findAll().size();
        // Create the Guest
        GuestDTO guestDTO = guestMapper.toDto(guest);
        restGuestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestDTO)))
            .andExpect(status().isCreated());

        // Validate the Guest in the database
        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeCreate + 1);
        Guest testGuest = guestList.get(guestList.size() - 1);
        assertThat(testGuest.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testGuest.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testGuest.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testGuest.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testGuest.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testGuest.getAddress2()).isEqualTo(DEFAULT_ADDRESS_2);
        assertThat(testGuest.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testGuest.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testGuest.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testGuest.getZip()).isEqualTo(DEFAULT_ZIP);
        assertThat(testGuest.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testGuest.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testGuest.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testGuest.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    void createGuestWithExistingId() throws Exception {
        // Create the Guest with an existing ID
        guest.setId(1L);
        GuestDTO guestDTO = guestMapper.toDto(guest);

        int databaseSizeBeforeCreate = guestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGuestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Guest in the database
        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGuests() throws Exception {
        // Initialize the database
        guestRepository.saveAndFlush(guest);

        // Get all the guestList
        restGuestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(guest.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].zip").value(hasItem(DEFAULT_ZIP)))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)));
    }

    @Test
    @Transactional
    void getGuest() throws Exception {
        // Initialize the database
        guestRepository.saveAndFlush(guest);

        // Get the guest
        restGuestMockMvc
            .perform(get(ENTITY_API_URL_ID, guest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(guest.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS_1))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS_2))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.zip").value(DEFAULT_ZIP))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingGuest() throws Exception {
        // Get the guest
        restGuestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGuest() throws Exception {
        // Initialize the database
        guestRepository.saveAndFlush(guest);

        int databaseSizeBeforeUpdate = guestRepository.findAll().size();

        // Update the guest
        Guest updatedGuest = guestRepository.findById(guest.getId()).get();
        // Disconnect from session so that the updates on updatedGuest are not directly saved in db
        em.detach(updatedGuest);
        updatedGuest
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .zip(UPDATED_ZIP)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY);
        GuestDTO guestDTO = guestMapper.toDto(updatedGuest);

        restGuestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, guestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(guestDTO))
            )
            .andExpect(status().isOk());

        // Validate the Guest in the database
        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeUpdate);
        Guest testGuest = guestList.get(guestList.size() - 1);
        assertThat(testGuest.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testGuest.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testGuest.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testGuest.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testGuest.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testGuest.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testGuest.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testGuest.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testGuest.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testGuest.getZip()).isEqualTo(UPDATED_ZIP);
        assertThat(testGuest.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testGuest.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testGuest.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testGuest.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void putNonExistingGuest() throws Exception {
        int databaseSizeBeforeUpdate = guestRepository.findAll().size();
        guest.setId(count.incrementAndGet());

        // Create the Guest
        GuestDTO guestDTO = guestMapper.toDto(guest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGuestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, guestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(guestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Guest in the database
        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGuest() throws Exception {
        int databaseSizeBeforeUpdate = guestRepository.findAll().size();
        guest.setId(count.incrementAndGet());

        // Create the Guest
        GuestDTO guestDTO = guestMapper.toDto(guest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(guestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Guest in the database
        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGuest() throws Exception {
        int databaseSizeBeforeUpdate = guestRepository.findAll().size();
        guest.setId(count.incrementAndGet());

        // Create the Guest
        GuestDTO guestDTO = guestMapper.toDto(guest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Guest in the database
        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGuestWithPatch() throws Exception {
        // Initialize the database
        guestRepository.saveAndFlush(guest);

        int databaseSizeBeforeUpdate = guestRepository.findAll().size();

        // Update the guest using partial update
        Guest partialUpdatedGuest = new Guest();
        partialUpdatedGuest.setId(guest.getId());

        partialUpdatedGuest
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .address2(UPDATED_ADDRESS_2)
            .state(UPDATED_STATE)
            .zip(UPDATED_ZIP)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restGuestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGuest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGuest))
            )
            .andExpect(status().isOk());

        // Validate the Guest in the database
        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeUpdate);
        Guest testGuest = guestList.get(guestList.size() - 1);
        assertThat(testGuest.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testGuest.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testGuest.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testGuest.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testGuest.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testGuest.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testGuest.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testGuest.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testGuest.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testGuest.getZip()).isEqualTo(UPDATED_ZIP);
        assertThat(testGuest.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testGuest.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testGuest.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testGuest.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void fullUpdateGuestWithPatch() throws Exception {
        // Initialize the database
        guestRepository.saveAndFlush(guest);

        int databaseSizeBeforeUpdate = guestRepository.findAll().size();

        // Update the guest using partial update
        Guest partialUpdatedGuest = new Guest();
        partialUpdatedGuest.setId(guest.getId());

        partialUpdatedGuest
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .zip(UPDATED_ZIP)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY);

        restGuestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGuest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGuest))
            )
            .andExpect(status().isOk());

        // Validate the Guest in the database
        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeUpdate);
        Guest testGuest = guestList.get(guestList.size() - 1);
        assertThat(testGuest.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testGuest.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testGuest.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testGuest.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testGuest.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testGuest.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testGuest.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testGuest.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testGuest.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testGuest.getZip()).isEqualTo(UPDATED_ZIP);
        assertThat(testGuest.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testGuest.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testGuest.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testGuest.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingGuest() throws Exception {
        int databaseSizeBeforeUpdate = guestRepository.findAll().size();
        guest.setId(count.incrementAndGet());

        // Create the Guest
        GuestDTO guestDTO = guestMapper.toDto(guest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGuestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, guestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(guestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Guest in the database
        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGuest() throws Exception {
        int databaseSizeBeforeUpdate = guestRepository.findAll().size();
        guest.setId(count.incrementAndGet());

        // Create the Guest
        GuestDTO guestDTO = guestMapper.toDto(guest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(guestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Guest in the database
        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGuest() throws Exception {
        int databaseSizeBeforeUpdate = guestRepository.findAll().size();
        guest.setId(count.incrementAndGet());

        // Create the Guest
        GuestDTO guestDTO = guestMapper.toDto(guest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(guestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Guest in the database
        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGuest() throws Exception {
        // Initialize the database
        guestRepository.saveAndFlush(guest);

        int databaseSizeBeforeDelete = guestRepository.findAll().size();

        // Delete the guest
        restGuestMockMvc
            .perform(delete(ENTITY_API_URL_ID, guest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
