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

    private static final String ENTITY_NAME = "bookingserviceReservationDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

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
     * {@code POST  /reservation-details} : Create a new reservationDetails.
     *
     * @param reservationDetailsDTO the reservationDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reservationDetailsDTO, or with status {@code 400 (Bad Request)} if the reservationDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reservation-details")
    public ResponseEntity<ReservationDetailsDTO> createReservationDetails(@RequestBody ReservationDetailsDTO reservationDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save ReservationDetails : {}", reservationDetailsDTO);
        if (reservationDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new reservationDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReservationDetailsDTO result = reservationDetailsService.save(reservationDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/reservation-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reservation-details/:id} : Updates an existing reservationDetails.
     *
     * @param id the id of the reservationDetailsDTO to save.
     * @param reservationDetailsDTO the reservationDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reservationDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the reservationDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reservationDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reservation-details/{id}")
    public ResponseEntity<ReservationDetailsDTO> updateReservationDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReservationDetailsDTO reservationDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ReservationDetails : {}, {}", id, reservationDetailsDTO);
        if (reservationDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reservationDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reservationDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReservationDetailsDTO result = reservationDetailsService.update(reservationDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reservationDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /reservation-details/:id} : Partial updates given fields of an existing reservationDetails, field will ignore if it is null
     *
     * @param id the id of the reservationDetailsDTO to save.
     * @param reservationDetailsDTO the reservationDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reservationDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the reservationDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the reservationDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the reservationDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/reservation-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReservationDetailsDTO> partialUpdateReservationDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReservationDetailsDTO reservationDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ReservationDetails partially : {}, {}", id, reservationDetailsDTO);
        if (reservationDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reservationDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reservationDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReservationDetailsDTO> result = reservationDetailsService.partialUpdate(reservationDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reservationDetailsDTO.getId().toString())
        );
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

    /**
     * {@code DELETE  /reservation-details/:id} : delete the "id" reservationDetails.
     *
     * @param id the id of the reservationDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reservation-details/{id}")
    public ResponseEntity<Void> deleteReservationDetails(@PathVariable Long id) {
        log.debug("REST request to delete ReservationDetails : {}", id);
        reservationDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
