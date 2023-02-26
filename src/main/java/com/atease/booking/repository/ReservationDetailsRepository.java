package com.atease.booking.repository;

import com.atease.booking.domain.ReservationDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ReservationDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReservationDetailsRepository extends JpaRepository<ReservationDetails, Long> {}
