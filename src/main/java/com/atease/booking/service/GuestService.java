package com.atease.booking.service;

import com.atease.booking.domain.Guest;
import com.atease.booking.repository.GuestRepository;
import com.atease.booking.service.dto.GuestDTO;
import com.atease.booking.service.mapper.GuestMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Guest}.
 */
@Service
@Transactional
public class GuestService {

    private final Logger log = LoggerFactory.getLogger(GuestService.class);

    private final GuestRepository guestRepository;

    private final GuestMapper guestMapper;

    public GuestService(GuestRepository guestRepository, GuestMapper guestMapper) {
        this.guestRepository = guestRepository;
        this.guestMapper = guestMapper;
    }

    /**
     * Save a guest.
     *
     * @param guestDTO the entity to save.
     * @return the persisted entity.
     */
    public GuestDTO save(GuestDTO guestDTO) {
        log.debug("Request to save Guest : {}", guestDTO);
        Guest guest = guestMapper.toEntity(guestDTO);
        guest = guestRepository.save(guest);
        return guestMapper.toDto(guest);
    }

    /**
     * Update a guest.
     *
     * @param guestDTO the entity to save.
     * @return the persisted entity.
     */
    public GuestDTO update(GuestDTO guestDTO) {
        log.debug("Request to update Guest : {}", guestDTO);
        Guest guest = guestMapper.toEntity(guestDTO);
        guest = guestRepository.save(guest);
        return guestMapper.toDto(guest);
    }

    /**
     * Partially update a guest.
     *
     * @param guestDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GuestDTO> partialUpdate(GuestDTO guestDTO) {
        log.debug("Request to partially update Guest : {}", guestDTO);

        return guestRepository
            .findById(guestDTO.getId())
            .map(existingGuest -> {
                guestMapper.partialUpdate(existingGuest, guestDTO);

                return existingGuest;
            })
            .map(guestRepository::save)
            .map(guestMapper::toDto);
    }

    /**
     * Get all the guests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GuestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Guests");
        return guestRepository.findAll(pageable).map(guestMapper::toDto);
    }

    /**
     * Get one guest by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GuestDTO> findOne(Long id) {
        log.debug("Request to get Guest : {}", id);
        return guestRepository.findById(id).map(guestMapper::toDto);
    }

    /**
     * Delete the guest by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Guest : {}", id);
        guestRepository.deleteById(id);
    }
}
