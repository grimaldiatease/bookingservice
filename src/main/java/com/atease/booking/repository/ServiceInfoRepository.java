package com.atease.booking.repository;

import com.atease.booking.domain.ServiceInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ServiceInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceInfoRepository extends JpaRepository<ServiceInfo, Long> {}
