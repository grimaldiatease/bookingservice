package com.atease.booking.web.rest;

import com.atease.booking.repository.ReservationDetailsRepository;
import com.atease.booking.service.ReservationDetailsService;
import com.atease.booking.service.dto.ReservationDetailsDTO;
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
 * REST controller for managing {@link com.atease.booking.domain.ReservationDetails}.
 */
@RestController
@RequestMapping("/api")
public class ReservationDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ReservationDetailsResource.class);

    private final ReservationDetailsService reservationDetailsService;

    private final ReservationDetailsRepository reservationDetailsRepository;

    public ReservationDetailsResource(
        ReservationDetailsService reservationDetailsService,
        ReservationDetailsRepository reservationDetailsRepository
    ) {
        this.reservationDetailsService = reservationDetailsService;
        this.reservationDetailsRepository = reservationDetailsRepository;
    }

    /**
     * {@code GET  /reservation-details} : get all the reservationDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reservationDetails in body.
     */
    @GetMapping("/reservation-details")
    public List<ReservationDetailsDTO> getAllReservationDetails() {
        log.debug("REST request to get all ReservationDetails");
        return reservationDetailsService.findAll();
    }

    /**
     * {@code GET  /reservation-details/:id} : get the "id" reservationDetails.
     *
     * @param id the id of the reservationDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reservationDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reservation-details/{id}")
    public ResponseEntity<ReservationDetailsDTO> getReservationDetails(@PathVariable Long id) {
        log.debug("REST request to get ReservationDetails : {}", id);
        Optional<ReservationDetailsDTO> reservationDetailsDTO = reservationDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reservationDetailsDTO);
    }
}
