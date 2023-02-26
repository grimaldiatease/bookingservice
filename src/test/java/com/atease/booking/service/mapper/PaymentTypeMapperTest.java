package com.atease.booking.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentTypeMapperTest {

    private PaymentTypeMapper paymentTypeMapper;

    @BeforeEach
    public void setUp() {
        paymentTypeMapper = new PaymentTypeMapperImpl();
    }
}
