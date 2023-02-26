package com.atease.booking.service;

import com.atease.booking.domain.PaymentType;
import com.atease.booking.repository.PaymentTypeRepository;
import com.atease.booking.service.dto.PaymentTypeDTO;
import com.atease.booking.service.mapper.PaymentTypeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PaymentType}.
 */
@Service
@Transactional
public class PaymentTypeService {

    private final Logger log = LoggerFactory.getLogger(PaymentTypeService.class);

    private final PaymentTypeRepository paymentTypeRepository;

    private final PaymentTypeMapper paymentTypeMapper;

    public PaymentTypeService(PaymentTypeRepository paymentTypeRepository, PaymentTypeMapper paymentTypeMapper) {
        this.paymentTypeRepository = paymentTypeRepository;
        this.paymentTypeMapper = paymentTypeMapper;
    }

    /**
     * Save a paymentType.
     *
     * @param paymentTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public PaymentTypeDTO save(PaymentTypeDTO paymentTypeDTO) {
        log.debug("Request to save PaymentType : {}", paymentTypeDTO);
        PaymentType paymentType = paymentTypeMapper.toEntity(paymentTypeDTO);
        paymentType = paymentTypeRepository.save(paymentType);
        return paymentTypeMapper.toDto(paymentType);
    }

    /**
     * Update a paymentType.
     *
     * @param paymentTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public PaymentTypeDTO update(PaymentTypeDTO paymentTypeDTO) {
        log.debug("Request to update PaymentType : {}", paymentTypeDTO);
        PaymentType paymentType = paymentTypeMapper.toEntity(paymentTypeDTO);
        paymentType = paymentTypeRepository.save(paymentType);
        return paymentTypeMapper.toDto(paymentType);
    }

    /**
     * Partially update a paymentType.
     *
     * @param paymentTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PaymentTypeDTO> partialUpdate(PaymentTypeDTO paymentTypeDTO) {
        log.debug("Request to partially update PaymentType : {}", paymentTypeDTO);

        return paymentTypeRepository
            .findById(paymentTypeDTO.getId())
            .map(existingPaymentType -> {
                paymentTypeMapper.partialUpdate(existingPaymentType, paymentTypeDTO);

                return existingPaymentType;
            })
            .map(paymentTypeRepository::save)
            .map(paymentTypeMapper::toDto);
    }

    /**
     * Get all the paymentTypes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PaymentTypeDTO> findAll() {
        log.debug("Request to get all PaymentTypes");
        return paymentTypeRepository.findAll().stream().map(paymentTypeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one paymentType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PaymentTypeDTO> findOne(Long id) {
        log.debug("Request to get PaymentType : {}", id);
        return paymentTypeRepository.findById(id).map(paymentTypeMapper::toDto);
    }

    /**
     * Delete the paymentType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PaymentType : {}", id);
        paymentTypeRepository.deleteById(id);
    }
}
