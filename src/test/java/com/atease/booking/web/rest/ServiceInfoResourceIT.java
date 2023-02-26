package com.atease.booking.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atease.booking.IntegrationTest;
import com.atease.booking.domain.ServiceInfo;
import com.atease.booking.repository.ServiceInfoRepository;
import com.atease.booking.service.dto.ServiceInfoDTO;
import com.atease.booking.service.mapper.ServiceInfoMapper;
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
 * Integration tests for the {@link ServiceInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServiceInfoResourceIT {

    private static final String DEFAULT_CURRENT_INSTALLATION = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_INSTALLATION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/service-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ServiceInfoRepository serviceInfoRepository;

    @Autowired
    private ServiceInfoMapper serviceInfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceInfoMockMvc;

    private ServiceInfo serviceInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceInfo createEntity(EntityManager em) {
        ServiceInfo serviceInfo = new ServiceInfo()
            .currentInstallation(DEFAULT_CURRENT_INSTALLATION)
            .modifiedDate(DEFAULT_MODIFIED_DATE)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY);
        return serviceInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceInfo createUpdatedEntity(EntityManager em) {
        ServiceInfo serviceInfo = new ServiceInfo()
            .currentInstallation(UPDATED_CURRENT_INSTALLATION)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY);
        return serviceInfo;
    }

    @BeforeEach
    public void initTest() {
        serviceInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createServiceInfo() throws Exception {
        int databaseSizeBeforeCreate = serviceInfoRepository.findAll().size();
        // Create the ServiceInfo
        ServiceInfoDTO serviceInfoDTO = serviceInfoMapper.toDto(serviceInfo);
        restServiceInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(serviceInfoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ServiceInfo in the database
        List<ServiceInfo> serviceInfoList = serviceInfoRepository.findAll();
        assertThat(serviceInfoList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceInfo testServiceInfo = serviceInfoList.get(serviceInfoList.size() - 1);
        assertThat(testServiceInfo.getCurrentInstallation()).isEqualTo(DEFAULT_CURRENT_INSTALLATION);
        assertThat(testServiceInfo.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testServiceInfo.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testServiceInfo.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testServiceInfo.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    void createServiceInfoWithExistingId() throws Exception {
        // Create the ServiceInfo with an existing ID
        serviceInfo.setId(1L);
        ServiceInfoDTO serviceInfoDTO = serviceInfoMapper.toDto(serviceInfo);

        int databaseSizeBeforeCreate = serviceInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(serviceInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceInfo in the database
        List<ServiceInfo> serviceInfoList = serviceInfoRepository.findAll();
        assertThat(serviceInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllServiceInfos() throws Exception {
        // Initialize the database
        serviceInfoRepository.saveAndFlush(serviceInfo);

        // Get all the serviceInfoList
        restServiceInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].currentInstallation").value(hasItem(DEFAULT_CURRENT_INSTALLATION)))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)));
    }

    @Test
    @Transactional
    void getServiceInfo() throws Exception {
        // Initialize the database
        serviceInfoRepository.saveAndFlush(serviceInfo);

        // Get the serviceInfo
        restServiceInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, serviceInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceInfo.getId().intValue()))
            .andExpect(jsonPath("$.currentInstallation").value(DEFAULT_CURRENT_INSTALLATION))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingServiceInfo() throws Exception {
        // Get the serviceInfo
        restServiceInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingServiceInfo() throws Exception {
        // Initialize the database
        serviceInfoRepository.saveAndFlush(serviceInfo);

        int databaseSizeBeforeUpdate = serviceInfoRepository.findAll().size();

        // Update the serviceInfo
        ServiceInfo updatedServiceInfo = serviceInfoRepository.findById(serviceInfo.getId()).get();
        // Disconnect from session so that the updates on updatedServiceInfo are not directly saved in db
        em.detach(updatedServiceInfo);
        updatedServiceInfo
            .currentInstallation(UPDATED_CURRENT_INSTALLATION)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY);
        ServiceInfoDTO serviceInfoDTO = serviceInfoMapper.toDto(updatedServiceInfo);

        restServiceInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(serviceInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the ServiceInfo in the database
        List<ServiceInfo> serviceInfoList = serviceInfoRepository.findAll();
        assertThat(serviceInfoList).hasSize(databaseSizeBeforeUpdate);
        ServiceInfo testServiceInfo = serviceInfoList.get(serviceInfoList.size() - 1);
        assertThat(testServiceInfo.getCurrentInstallation()).isEqualTo(UPDATED_CURRENT_INSTALLATION);
        assertThat(testServiceInfo.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testServiceInfo.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testServiceInfo.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testServiceInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void putNonExistingServiceInfo() throws Exception {
        int databaseSizeBeforeUpdate = serviceInfoRepository.findAll().size();
        serviceInfo.setId(count.incrementAndGet());

        // Create the ServiceInfo
        ServiceInfoDTO serviceInfoDTO = serviceInfoMapper.toDto(serviceInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(serviceInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceInfo in the database
        List<ServiceInfo> serviceInfoList = serviceInfoRepository.findAll();
        assertThat(serviceInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServiceInfo() throws Exception {
        int databaseSizeBeforeUpdate = serviceInfoRepository.findAll().size();
        serviceInfo.setId(count.incrementAndGet());

        // Create the ServiceInfo
        ServiceInfoDTO serviceInfoDTO = serviceInfoMapper.toDto(serviceInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(serviceInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceInfo in the database
        List<ServiceInfo> serviceInfoList = serviceInfoRepository.findAll();
        assertThat(serviceInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServiceInfo() throws Exception {
        int databaseSizeBeforeUpdate = serviceInfoRepository.findAll().size();
        serviceInfo.setId(count.incrementAndGet());

        // Create the ServiceInfo
        ServiceInfoDTO serviceInfoDTO = serviceInfoMapper.toDto(serviceInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(serviceInfoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceInfo in the database
        List<ServiceInfo> serviceInfoList = serviceInfoRepository.findAll();
        assertThat(serviceInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServiceInfoWithPatch() throws Exception {
        // Initialize the database
        serviceInfoRepository.saveAndFlush(serviceInfo);

        int databaseSizeBeforeUpdate = serviceInfoRepository.findAll().size();

        // Update the serviceInfo using partial update
        ServiceInfo partialUpdatedServiceInfo = new ServiceInfo();
        partialUpdatedServiceInfo.setId(serviceInfo.getId());

        partialUpdatedServiceInfo
            .currentInstallation(UPDATED_CURRENT_INSTALLATION)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY);

        restServiceInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServiceInfo))
            )
            .andExpect(status().isOk());

        // Validate the ServiceInfo in the database
        List<ServiceInfo> serviceInfoList = serviceInfoRepository.findAll();
        assertThat(serviceInfoList).hasSize(databaseSizeBeforeUpdate);
        ServiceInfo testServiceInfo = serviceInfoList.get(serviceInfoList.size() - 1);
        assertThat(testServiceInfo.getCurrentInstallation()).isEqualTo(UPDATED_CURRENT_INSTALLATION);
        assertThat(testServiceInfo.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testServiceInfo.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testServiceInfo.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testServiceInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void fullUpdateServiceInfoWithPatch() throws Exception {
        // Initialize the database
        serviceInfoRepository.saveAndFlush(serviceInfo);

        int databaseSizeBeforeUpdate = serviceInfoRepository.findAll().size();

        // Update the serviceInfo using partial update
        ServiceInfo partialUpdatedServiceInfo = new ServiceInfo();
        partialUpdatedServiceInfo.setId(serviceInfo.getId());

        partialUpdatedServiceInfo
            .currentInstallation(UPDATED_CURRENT_INSTALLATION)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY);

        restServiceInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServiceInfo))
            )
            .andExpect(status().isOk());

        // Validate the ServiceInfo in the database
        List<ServiceInfo> serviceInfoList = serviceInfoRepository.findAll();
        assertThat(serviceInfoList).hasSize(databaseSizeBeforeUpdate);
        ServiceInfo testServiceInfo = serviceInfoList.get(serviceInfoList.size() - 1);
        assertThat(testServiceInfo.getCurrentInstallation()).isEqualTo(UPDATED_CURRENT_INSTALLATION);
        assertThat(testServiceInfo.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testServiceInfo.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testServiceInfo.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testServiceInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingServiceInfo() throws Exception {
        int databaseSizeBeforeUpdate = serviceInfoRepository.findAll().size();
        serviceInfo.setId(count.incrementAndGet());

        // Create the ServiceInfo
        ServiceInfoDTO serviceInfoDTO = serviceInfoMapper.toDto(serviceInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, serviceInfoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(serviceInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceInfo in the database
        List<ServiceInfo> serviceInfoList = serviceInfoRepository.findAll();
        assertThat(serviceInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServiceInfo() throws Exception {
        int databaseSizeBeforeUpdate = serviceInfoRepository.findAll().size();
        serviceInfo.setId(count.incrementAndGet());

        // Create the ServiceInfo
        ServiceInfoDTO serviceInfoDTO = serviceInfoMapper.toDto(serviceInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(serviceInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceInfo in the database
        List<ServiceInfo> serviceInfoList = serviceInfoRepository.findAll();
        assertThat(serviceInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServiceInfo() throws Exception {
        int databaseSizeBeforeUpdate = serviceInfoRepository.findAll().size();
        serviceInfo.setId(count.incrementAndGet());

        // Create the ServiceInfo
        ServiceInfoDTO serviceInfoDTO = serviceInfoMapper.toDto(serviceInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceInfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(serviceInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceInfo in the database
        List<ServiceInfo> serviceInfoList = serviceInfoRepository.findAll();
        assertThat(serviceInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServiceInfo() throws Exception {
        // Initialize the database
        serviceInfoRepository.saveAndFlush(serviceInfo);

        int databaseSizeBeforeDelete = serviceInfoRepository.findAll().size();

        // Delete the serviceInfo
        restServiceInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, serviceInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceInfo> serviceInfoList = serviceInfoRepository.findAll();
        assertThat(serviceInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
