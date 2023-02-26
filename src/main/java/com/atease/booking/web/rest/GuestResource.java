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

    private final GuestService guestService;

    private final GuestRepository guestRepository;

    public GuestResource(GuestService guestService, GuestRepository guestRepository) {
        this.guestService = guestService;
        this.guestRepository = guestRepository;
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
}
