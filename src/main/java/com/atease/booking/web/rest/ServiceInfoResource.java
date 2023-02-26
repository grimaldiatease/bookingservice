package com.atease.booking.web.rest;

import com.atease.booking.repository.ServiceInfoRepository;
import com.atease.booking.service.ServiceInfoService;
import com.atease.booking.service.dto.ServiceInfoDTO;
import com.atease.booking.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.atease.booking.domain.ServiceInfo}.
 */
@RestController
@RequestMapping("/api")
public class ServiceInfoResource {

    private final Logger log = LoggerFactory.getLogger(ServiceInfoResource.class);

    private static final String ENTITY_NAME = "bookingserviceServiceInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceInfoService serviceInfoService;

    private final ServiceInfoRepository serviceInfoRepository;

    public ServiceInfoResource(ServiceInfoService serviceInfoService, ServiceInfoRepository serviceInfoRepository) {
        this.serviceInfoService = serviceInfoService;
        this.serviceInfoRepository = serviceInfoRepository;
    }

    /**
     * {@code POST  /service-infos} : Create a new serviceInfo.
     *
     * @param serviceInfoDTO the serviceInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceInfoDTO, or with status {@code 400 (Bad Request)} if the serviceInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-infos")
    public ResponseEntity<ServiceInfoDTO> createServiceInfo(@RequestBody ServiceInfoDTO serviceInfoDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceInfo : {}", serviceInfoDTO);
        if (serviceInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceInfoDTO result = serviceInfoService.save(serviceInfoDTO);
        return ResponseEntity
            .created(new URI("/api/service-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-infos/:id} : Updates an existing serviceInfo.
     *
     * @param id the id of the serviceInfoDTO to save.
     * @param serviceInfoDTO the serviceInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceInfoDTO,
     * or with status {@code 400 (Bad Request)} if the serviceInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-infos/{id}")
    public ResponseEntity<ServiceInfoDTO> updateServiceInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ServiceInfoDTO serviceInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ServiceInfo : {}, {}", id, serviceInfoDTO);
        if (serviceInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ServiceInfoDTO result = serviceInfoService.update(serviceInfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /service-infos/:id} : Partial updates given fields of an existing serviceInfo, field will ignore if it is null
     *
     * @param id the id of the serviceInfoDTO to save.
     * @param serviceInfoDTO the serviceInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceInfoDTO,
     * or with status {@code 400 (Bad Request)} if the serviceInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the serviceInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the serviceInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/service-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServiceInfoDTO> partialUpdateServiceInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ServiceInfoDTO serviceInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ServiceInfo partially : {}, {}", id, serviceInfoDTO);
        if (serviceInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServiceInfoDTO> result = serviceInfoService.partialUpdate(serviceInfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceInfoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /service-infos} : get all the serviceInfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceInfos in body.
     */
    @GetMapping("/service-infos")
    public List<ServiceInfoDTO> getAllServiceInfos() {
        log.debug("REST request to get all ServiceInfos");
        return serviceInfoService.findAll();
    }

    /**
     * {@code GET  /service-infos/:id} : get the "id" serviceInfo.
     *
     * @param id the id of the serviceInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-infos/{id}")
    public ResponseEntity<ServiceInfoDTO> getServiceInfo(@PathVariable Long id) {
        log.debug("REST request to get ServiceInfo : {}", id);
        Optional<ServiceInfoDTO> serviceInfoDTO = serviceInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceInfoDTO);
    }

    /**
     * {@code DELETE  /service-infos/:id} : delete the "id" serviceInfo.
     *
     * @param id the id of the serviceInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-infos/{id}")
    public ResponseEntity<Void> deleteServiceInfo(@PathVariable Long id) {
        log.debug("REST request to delete ServiceInfo : {}", id);
        serviceInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
