package com.atease.booking.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atease.booking.IntegrationTest;
import com.atease.booking.domain.PaymentType;
import com.atease.booking.repository.PaymentTypeRepository;
import com.atease.booking.service.dto.PaymentTypeDTO;
import com.atease.booking.service.mapper.PaymentTypeMapper;
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
 * Integration tests for the {@link PaymentTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/payment-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentTypeRepository paymentTypeRepository;

    @Autowired
    private PaymentTypeMapper paymentTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentTypeMockMvc;

    private PaymentType paymentType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentType createEntity(EntityManager em) {
        PaymentType paymentType = new PaymentType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .modifiedDate(DEFAULT_MODIFIED_DATE)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY);
        return paymentType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentType createUpdatedEntity(EntityManager em) {
        PaymentType paymentType = new PaymentType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY);
        return paymentType;
    }

    @BeforeEach
    public void initTest() {
        paymentType = createEntity(em);
    }

    @Test
    @Transactional
    void createPaymentType() throws Exception {
        int databaseSizeBeforeCreate = paymentTypeRepository.findAll().size();
        // Create the PaymentType
        PaymentTypeDTO paymentTypeDTO = paymentTypeMapper.toDto(paymentType);
        restPaymentTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentType testPaymentType = paymentTypeList.get(paymentTypeList.size() - 1);
        assertThat(testPaymentType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPaymentType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPaymentType.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testPaymentType.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPaymentType.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testPaymentType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    void createPaymentTypeWithExistingId() throws Exception {
        // Create the PaymentType with an existing ID
        paymentType.setId(1L);
        PaymentTypeDTO paymentTypeDTO = paymentTypeMapper.toDto(paymentType);

        int databaseSizeBeforeCreate = paymentTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPaymentTypes() throws Exception {
        // Initialize the database
        paymentTypeRepository.saveAndFlush(paymentType);

        // Get all the paymentTypeList
        restPaymentTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)));
    }

    @Test
    @Transactional
    void getPaymentType() throws Exception {
        // Initialize the database
        paymentTypeRepository.saveAndFlush(paymentType);

        // Get the paymentType
        restPaymentTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingPaymentType() throws Exception {
        // Get the paymentType
        restPaymentTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPaymentType() throws Exception {
        // Initialize the database
        paymentTypeRepository.saveAndFlush(paymentType);

        int databaseSizeBeforeUpdate = paymentTypeRepository.findAll().size();

        // Update the paymentType
        PaymentType updatedPaymentType = paymentTypeRepository.findById(paymentType.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentType are not directly saved in db
        em.detach(updatedPaymentType);
        updatedPaymentType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY);
        PaymentTypeDTO paymentTypeDTO = paymentTypeMapper.toDto(updatedPaymentType);

        restPaymentTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeUpdate);
        PaymentType testPaymentType = paymentTypeList.get(paymentTypeList.size() - 1);
        assertThat(testPaymentType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaymentType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPaymentType.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testPaymentType.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPaymentType.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPaymentType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void putNonExistingPaymentType() throws Exception {
        int databaseSizeBeforeUpdate = paymentTypeRepository.findAll().size();
        paymentType.setId(count.incrementAndGet());

        // Create the PaymentType
        PaymentTypeDTO paymentTypeDTO = paymentTypeMapper.toDto(paymentType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentType() throws Exception {
        int databaseSizeBeforeUpdate = paymentTypeRepository.findAll().size();
        paymentType.setId(count.incrementAndGet());

        // Create the PaymentType
        PaymentTypeDTO paymentTypeDTO = paymentTypeMapper.toDto(paymentType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentType() throws Exception {
        int databaseSizeBeforeUpdate = paymentTypeRepository.findAll().size();
        paymentType.setId(count.incrementAndGet());

        // Create the PaymentType
        PaymentTypeDTO paymentTypeDTO = paymentTypeMapper.toDto(paymentType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentTypeWithPatch() throws Exception {
        // Initialize the database
        paymentTypeRepository.saveAndFlush(paymentType);

        int databaseSizeBeforeUpdate = paymentTypeRepository.findAll().size();

        // Update the paymentType using partial update
        PaymentType partialUpdatedPaymentType = new PaymentType();
        partialUpdatedPaymentType.setId(paymentType.getId());

        partialUpdatedPaymentType.name(UPDATED_NAME).createdDate(UPDATED_CREATED_DATE).createdBy(UPDATED_CREATED_BY);

        restPaymentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentType))
            )
            .andExpect(status().isOk());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeUpdate);
        PaymentType testPaymentType = paymentTypeList.get(paymentTypeList.size() - 1);
        assertThat(testPaymentType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaymentType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPaymentType.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testPaymentType.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPaymentType.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testPaymentType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void fullUpdatePaymentTypeWithPatch() throws Exception {
        // Initialize the database
        paymentTypeRepository.saveAndFlush(paymentType);

        int databaseSizeBeforeUpdate = paymentTypeRepository.findAll().size();

        // Update the paymentType using partial update
        PaymentType partialUpdatedPaymentType = new PaymentType();
        partialUpdatedPaymentType.setId(paymentType.getId());

        partialUpdatedPaymentType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY);

        restPaymentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentType))
            )
            .andExpect(status().isOk());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeUpdate);
        PaymentType testPaymentType = paymentTypeList.get(paymentTypeList.size() - 1);
        assertThat(testPaymentType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaymentType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPaymentType.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testPaymentType.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPaymentType.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPaymentType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingPaymentType() throws Exception {
        int databaseSizeBeforeUpdate = paymentTypeRepository.findAll().size();
        paymentType.setId(count.incrementAndGet());

        // Create the PaymentType
        PaymentTypeDTO paymentTypeDTO = paymentTypeMapper.toDto(paymentType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentType() throws Exception {
        int databaseSizeBeforeUpdate = paymentTypeRepository.findAll().size();
        paymentType.setId(count.incrementAndGet());

        // Create the PaymentType
        PaymentTypeDTO paymentTypeDTO = paymentTypeMapper.toDto(paymentType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentType() throws Exception {
        int databaseSizeBeforeUpdate = paymentTypeRepository.findAll().size();
        paymentType.setId(count.incrementAndGet());

        // Create the PaymentType
        PaymentTypeDTO paymentTypeDTO = paymentTypeMapper.toDto(paymentType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(paymentTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentType() throws Exception {
        // Initialize the database
        paymentTypeRepository.saveAndFlush(paymentType);

        int databaseSizeBeforeDelete = paymentTypeRepository.findAll().size();

        // Delete the paymentType
        restPaymentTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
