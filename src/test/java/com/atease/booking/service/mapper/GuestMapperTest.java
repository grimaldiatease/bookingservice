package com.atease.booking.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GuestMapperTest {

    private GuestMapper guestMapper;

    @BeforeEach
    public void setUp() {
        guestMapper = new GuestMapperImpl();
    }
}
