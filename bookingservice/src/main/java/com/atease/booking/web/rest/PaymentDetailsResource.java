package com.atease.booking.web.rest;

import com.atease.booking.repository.PaymentDetailsRepository;
import com.atease.booking.service.PaymentDetailsService;
import com.atease.booking.service.dto.PaymentDetailsDTO;
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
 * REST controller for managing {@link com.atease.booking.domain.PaymentDetails}.
 */
@RestController
@RequestMapping("/api")
public class PaymentDetailsResource {

    private final Logger log = LoggerFactory.getLogger(PaymentDetailsResource.class);

    private static final String ENTITY_NAME = "bookingservicePaymentDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentDetailsService paymentDetailsService;

    private final PaymentDetailsRepository paymentDetailsRepository;

    public PaymentDetailsResource(PaymentDetailsService paymentDetailsService, PaymentDetailsRepository paymentDetailsRepository) {
        this.paymentDetailsService = paymentDetailsService;
        this.paymentDetailsRepository = paymentDetailsRepository;
    }

    /**
     * {@code POST  /payment-details} : Create a new paymentDetails.
     *
     * @param paymentDetailsDTO the paymentDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentDetailsDTO, or with status {@code 400 (Bad Request)} if the paymentDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-details")
    public ResponseEntity<PaymentDetailsDTO> createPaymentDetails(@RequestBody PaymentDetailsDTO paymentDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save PaymentDetails : {}", paymentDetailsDTO);
        if (paymentDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentDetailsDTO result = paymentDetailsService.save(paymentDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/payment-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-details/:id} : Updates an existing paymentDetails.
     *
     * @param id the id of the paymentDetailsDTO to save.
     * @param paymentDetailsDTO the paymentDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the paymentDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-details/{id}")
    public ResponseEntity<PaymentDetailsDTO> updatePaymentDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentDetailsDTO paymentDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentDetails : {}, {}", id, paymentDetailsDTO);
        if (paymentDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaymentDetailsDTO result = paymentDetailsService.update(paymentDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payment-details/:id} : Partial updates given fields of an existing paymentDetails, field will ignore if it is null
     *
     * @param id the id of the paymentDetailsDTO to save.
     * @param paymentDetailsDTO the paymentDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the paymentDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paymentDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payment-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentDetailsDTO> partialUpdatePaymentDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentDetailsDTO paymentDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentDetails partially : {}, {}", id, paymentDetailsDTO);
        if (paymentDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentDetailsDTO> result = paymentDetailsService.partialUpdate(paymentDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-details} : get all the paymentDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentDetails in body.
     */
    @GetMapping("/payment-details")
    public List<PaymentDetailsDTO> getAllPaymentDetails() {
        log.debug("REST request to get all PaymentDetails");
        return paymentDetailsService.findAll();
    }

    /**
     * {@code GET  /payment-details/:id} : get the "id" paymentDetails.
     *
     * @param id the id of the paymentDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-details/{id}")
    public ResponseEntity<PaymentDetailsDTO> getPaymentDetails(@PathVariable Long id) {
        log.debug("REST request to get PaymentDetails : {}", id);
        Optional<PaymentDetailsDTO> paymentDetailsDTO = paymentDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentDetailsDTO);
    }

    /**
     * {@code DELETE  /payment-details/:id} : delete the "id" paymentDetails.
     *
     * @param id the id of the paymentDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-details/{id}")
    public ResponseEntity<Void> deletePaymentDetails(@PathVariable Long id) {
        log.debug("REST request to delete PaymentDetails : {}", id);
        paymentDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
