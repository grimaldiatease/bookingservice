package com.atease.booking.repository;

import com.atease.booking.domain.Guest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Guest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {}
