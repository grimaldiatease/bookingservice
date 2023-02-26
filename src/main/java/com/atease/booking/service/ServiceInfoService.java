package com.atease.booking.service;

import com.atease.booking.domain.ServiceInfo;
import com.atease.booking.repository.ServiceInfoRepository;
import com.atease.booking.service.dto.ServiceInfoDTO;
import com.atease.booking.service.mapper.ServiceInfoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ServiceInfo}.
 */
@Service
@Transactional
public class ServiceInfoService {

    private final Logger log = LoggerFactory.getLogger(ServiceInfoService.class);

    private final ServiceInfoRepository serviceInfoRepository;

    private final ServiceInfoMapper serviceInfoMapper;

    public ServiceInfoService(ServiceInfoRepository serviceInfoRepository, ServiceInfoMapper serviceInfoMapper) {
        this.serviceInfoRepository = serviceInfoRepository;
        this.serviceInfoMapper = serviceInfoMapper;
    }

    /**
     * Save a serviceInfo.
     *
     * @param serviceInfoDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceInfoDTO save(ServiceInfoDTO serviceInfoDTO) {
        log.debug("Request to save ServiceInfo : {}", serviceInfoDTO);
        ServiceInfo serviceInfo = serviceInfoMapper.toEntity(serviceInfoDTO);
        serviceInfo = serviceInfoRepository.save(serviceInfo);
        return serviceInfoMapper.toDto(serviceInfo);
    }

    /**
     * Update a serviceInfo.
     *
     * @param serviceInfoDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceInfoDTO update(ServiceInfoDTO serviceInfoDTO) {
        log.debug("Request to update ServiceInfo : {}", serviceInfoDTO);
        ServiceInfo serviceInfo = serviceInfoMapper.toEntity(serviceInfoDTO);
        serviceInfo = serviceInfoRepository.save(serviceInfo);
        return serviceInfoMapper.toDto(serviceInfo);
    }

    /**
     * Partially update a serviceInfo.
     *
     * @param serviceInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ServiceInfoDTO> partialUpdate(ServiceInfoDTO serviceInfoDTO) {
        log.debug("Request to partially update ServiceInfo : {}", serviceInfoDTO);

        return serviceInfoRepository
            .findById(serviceInfoDTO.getId())
            .map(existingServiceInfo -> {
                serviceInfoMapper.partialUpdate(existingServiceInfo, serviceInfoDTO);

                return existingServiceInfo;
            })
            .map(serviceInfoRepository::save)
            .map(serviceInfoMapper::toDto);
    }

    /**
     * Get all the serviceInfos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceInfoDTO> findAll() {
        log.debug("Request to get all ServiceInfos");
        return serviceInfoRepository.findAll().stream().map(serviceInfoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one serviceInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceInfoDTO> findOne(Long id) {
        log.debug("Request to get ServiceInfo : {}", id);
        return serviceInfoRepository.findById(id).map(serviceInfoMapper::toDto);
    }

    /**
     * Delete the serviceInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceInfo : {}", id);
        serviceInfoRepository.deleteById(id);
    }
}
