package com.atease.booking.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentDetailsMapperTest {

    private PaymentDetailsMapper paymentDetailsMapper;

    @BeforeEach
    public void setUp() {
        paymentDetailsMapper = new PaymentDetailsMapperImpl();
    }
}
