package com.atease.booking.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServiceInfoMapperTest {

    private ServiceInfoMapper serviceInfoMapper;

    @BeforeEach
    public void setUp() {
        serviceInfoMapper = new ServiceInfoMapperImpl();
    }
}
