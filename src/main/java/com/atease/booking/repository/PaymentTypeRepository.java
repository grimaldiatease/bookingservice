package com.atease.booking.repository;

import com.atease.booking.domain.PaymentType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PaymentType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {}
