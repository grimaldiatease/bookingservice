package com.atease.booking.repository;

import com.atease.booking.domain.PaymentDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PaymentDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long> {}
