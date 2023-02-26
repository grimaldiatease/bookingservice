package com.atease.booking.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReservationDetailsMapperTest {

    private ReservationDetailsMapper reservationDetailsMapper;

    @BeforeEach
    public void setUp() {
        reservationDetailsMapper = new ReservationDetailsMapperImpl();
    }
}
