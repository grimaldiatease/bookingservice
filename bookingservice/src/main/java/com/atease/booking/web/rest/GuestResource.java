package com.atease.booking.web.rest;

import com.atease.booking.repository.GuestRepository;
import com.atease.booking.service.GuestService;
import com.atease.booking.service.dto.GuestDTO;
import com.atease.booking.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.atease.booking.domain.Guest}.
 */
@RestController
@RequestMapping("/api")
public class GuestResource {

    private final Logger log = LoggerFactory.getLogger(GuestResource.class);

    private static final String ENTITY_NAME = "bookingserviceGuest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GuestService guestService;

    private final GuestRepository guestRepository;

    public GuestResource(GuestService guestService, GuestRepository guestRepository) {
        this.guestService = guestService;
        this.guestRepository = guestRepository;
    }

    /**
     * {@code POST  /guests} : Create a new guest.
     *
     * @param guestDTO the guestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new guestDTO, or with status {@code 400 (Bad Request)} if the guest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/guests")
    public ResponseEntity<GuestDTO> createGuest(@RequestBody GuestDTO guestDTO) throws URISyntaxException {
        log.debug("REST request to save Guest : {}", guestDTO);
        if (guestDTO.getId() != null) {
            throw new BadRequestAlertException("A new guest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GuestDTO result = guestService.save(guestDTO);
        return ResponseEntity
            .created(new URI("/api/guests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /guests/:id} : Updates an existing guest.
     *
     * @param id the id of the guestDTO to save.
     * @param guestDTO the guestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated guestDTO,
     * or with status {@code 400 (Bad Request)} if the guestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the guestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/guests/{id}")
    public ResponseEntity<GuestDTO> updateGuest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GuestDTO guestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Guest : {}, {}", id, guestDTO);
        if (guestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, guestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!guestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GuestDTO result = guestService.update(guestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, guestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /guests/:id} : Partial updates given fields of an existing guest, field will ignore if it is null
     *
     * @param id the id of the guestDTO to save.
     * @param guestDTO the guestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated guestDTO,
     * or with status {@code 400 (Bad Request)} if the guestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the guestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the guestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/guests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GuestDTO> partialUpdateGuest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GuestDTO guestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Guest partially : {}, {}", id, guestDTO);
        if (guestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, guestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!guestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GuestDTO> result = guestService.partialUpdate(guestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, guestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /guests} : get all the guests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of guests in body.
     */
    @GetMapping("/guests")
    public ResponseEntity<List<GuestDTO>> getAllGuests(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Guests");
        Page<GuestDTO> page = guestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /guests/:id} : get the "id" guest.
     *
     * @param id the id of the guestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the guestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/guests/{id}")
    public ResponseEntity<GuestDTO> getGuest(@PathVariable Long id) {
        log.debug("REST request to get Guest : {}", id);
        Optional<GuestDTO> guestDTO = guestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(guestDTO);
    }

    /**
     * {@code DELETE  /guests/:id} : delete the "id" guest.
     *
     * @param id the id of the guestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/guests/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable Long id) {
        log.debug("REST request to delete Guest : {}", id);
        guestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
