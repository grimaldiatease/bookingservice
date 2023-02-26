package com.atease.booking.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.atease.booking.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServiceInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceInfo.class);
        ServiceInfo serviceInfo1 = new ServiceInfo();
        serviceInfo1.setId(1L);
        ServiceInfo serviceInfo2 = new ServiceInfo();
        serviceInfo2.setId(serviceInfo1.getId());
        assertThat(serviceInfo1).isEqualTo(serviceInfo2);
        serviceInfo2.setId(2L);
        assertThat(serviceInfo1).isNotEqualTo(serviceInfo2);
        serviceInfo1.setId(null);
        assertThat(serviceInfo1).isNotEqualTo(serviceInfo2);
    }
}
