package com.atease.booking.service;

import com.atease.booking.domain.ReservationDetails;
import com.atease.booking.repository.ReservationDetailsRepository;
import com.atease.booking.service.dto.ReservationDetailsDTO;
import com.atease.booking.service.mapper.ReservationDetailsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ReservationDetails}.
 */
@Service
@Transactional
public class ReservationDetailsService {

    private final Logger log = LoggerFactory.getLogger(ReservationDetailsService.class);

    private final ReservationDetailsRepository reservationDetailsRepository;

    private final ReservationDetailsMapper reservationDetailsMapper;

    public ReservationDetailsService(
        ReservationDetailsRepository reservationDetailsRepository,
        ReservationDetailsMapper reservationDetailsMapper
    ) {
        this.reservationDetailsRepository = reservationDetailsRepository;
        this.reservationDetailsMapper = reservationDetailsMapper;
    }

    /**
     * Save a reservationDetails.
     *
     * @param reservationDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public ReservationDetailsDTO save(ReservationDetailsDTO reservationDetailsDTO) {
        log.debug("Request to save ReservationDetails : {}", reservationDetailsDTO);
        ReservationDetails reservationDetails = reservationDetailsMapper.toEntity(reservationDetailsDTO);
        reservationDetails = reservationDetailsRepository.save(reservationDetails);
        return reservationDetailsMapper.toDto(reservationDetails);
    }

    /**
     * Update a reservationDetails.
     *
     * @param reservationDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public ReservationDetailsDTO update(ReservationDetailsDTO reservationDetailsDTO) {
        log.debug("Request to update ReservationDetails : {}", reservationDetailsDTO);
        ReservationDetails reservationDetails = reservationDetailsMapper.toEntity(reservationDetailsDTO);
        reservationDetails = reservationDetailsRepository.save(reservationDetails);
        return reservationDetailsMapper.toDto(reservationDetails);
    }

    /**
     * Partially update a reservationDetails.
     *
     * @param reservationDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ReservationDetailsDTO> partialUpdate(ReservationDetailsDTO reservationDetailsDTO) {
        log.debug("Request to partially update ReservationDetails : {}", reservationDetailsDTO);

        return reservationDetailsRepository
            .findById(reservationDetailsDTO.getId())
            .map(existingReservationDetails -> {
                reservationDetailsMapper.partialUpdate(existingReservationDetails, reservationDetailsDTO);

                return existingReservationDetails;
            })
            .map(reservationDetailsRepository::save)
            .map(reservationDetailsMapper::toDto);
    }

    /**
     * Get all the reservationDetails.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ReservationDetailsDTO> findAll() {
        log.debug("Request to get all ReservationDetails");
        return reservationDetailsRepository
            .findAll()
            .stream()
            .map(reservationDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one reservationDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ReservationDetailsDTO> findOne(Long id) {
        log.debug("Request to get ReservationDetails : {}", id);
        return reservationDetailsRepository.findById(id).map(reservationDetailsMapper::toDto);
    }

    /**
     * Delete the reservationDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ReservationDetails : {}", id);
        reservationDetailsRepository.deleteById(id);
    }
}
