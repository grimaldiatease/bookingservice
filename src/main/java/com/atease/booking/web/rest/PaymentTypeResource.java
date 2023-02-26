package com.atease.booking.web.rest;

import com.atease.booking.repository.PaymentTypeRepository;
import com.atease.booking.service.PaymentTypeService;
import com.atease.booking.service.dto.PaymentTypeDTO;
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
 * REST controller for managing {@link com.atease.booking.domain.PaymentType}.
 */
@RestController
@RequestMapping("/api")
public class PaymentTypeResource {

    private final Logger log = LoggerFactory.getLogger(PaymentTypeResource.class);

    private static final String ENTITY_NAME = "bookingservicePaymentType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentTypeService paymentTypeService;

    private final PaymentTypeRepository paymentTypeRepository;

    public PaymentTypeResource(PaymentTypeService paymentTypeService, PaymentTypeRepository paymentTypeRepository) {
        this.paymentTypeService = paymentTypeService;
        this.paymentTypeRepository = paymentTypeRepository;
    }

    /**
     * {@code POST  /payment-types} : Create a new paymentType.
     *
     * @param paymentTypeDTO the paymentTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentTypeDTO, or with status {@code 400 (Bad Request)} if the paymentType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-types")
    public ResponseEntity<PaymentTypeDTO> createPaymentType(@RequestBody PaymentTypeDTO paymentTypeDTO) throws URISyntaxException {
        log.debug("REST request to save PaymentType : {}", paymentTypeDTO);
        if (paymentTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentTypeDTO result = paymentTypeService.save(paymentTypeDTO);
        return ResponseEntity
            .created(new URI("/api/payment-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-types/:id} : Updates an existing paymentType.
     *
     * @param id the id of the paymentTypeDTO to save.
     * @param paymentTypeDTO the paymentTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentTypeDTO,
     * or with status {@code 400 (Bad Request)} if the paymentTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-types/{id}")
    public ResponseEntity<PaymentTypeDTO> updatePaymentType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentTypeDTO paymentTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentType : {}, {}", id, paymentTypeDTO);
        if (paymentTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaymentTypeDTO result = paymentTypeService.update(paymentTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payment-types/:id} : Partial updates given fields of an existing paymentType, field will ignore if it is null
     *
     * @param id the id of the paymentTypeDTO to save.
     * @param paymentTypeDTO the paymentTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentTypeDTO,
     * or with status {@code 400 (Bad Request)} if the paymentTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paymentTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payment-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentTypeDTO> partialUpdatePaymentType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentTypeDTO paymentTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentType partially : {}, {}", id, paymentTypeDTO);
        if (paymentTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentTypeDTO> result = paymentTypeService.partialUpdate(paymentTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-types} : get all the paymentTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentTypes in body.
     */
    @GetMapping("/payment-types")
    public List<PaymentTypeDTO> getAllPaymentTypes() {
        log.debug("REST request to get all PaymentTypes");
        return paymentTypeService.findAll();
    }

    /**
     * {@code GET  /payment-types/:id} : get the "id" paymentType.
     *
     * @param id the id of the paymentTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-types/{id}")
    public ResponseEntity<PaymentTypeDTO> getPaymentType(@PathVariable Long id) {
        log.debug("REST request to get PaymentType : {}", id);
        Optional<PaymentTypeDTO> paymentTypeDTO = paymentTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentTypeDTO);
    }

    /**
     * {@code DELETE  /payment-types/:id} : delete the "id" paymentType.
     *
     * @param id the id of the paymentTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-types/{id}")
    public ResponseEntity<Void> deletePaymentType(@PathVariable Long id) {
        log.debug("REST request to delete PaymentType : {}", id);
        paymentTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
