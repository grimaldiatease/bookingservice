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

    private final PaymentDetailsService paymentDetailsService;

    private final PaymentDetailsRepository paymentDetailsRepository;

    public PaymentDetailsResource(PaymentDetailsService paymentDetailsService, PaymentDetailsRepository paymentDetailsRepository) {
        this.paymentDetailsService = paymentDetailsService;
        this.paymentDetailsRepository = paymentDetailsRepository;
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
}
