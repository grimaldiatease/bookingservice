package com.atease.booking.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.atease.booking.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServiceInfoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceInfoDTO.class);
        ServiceInfoDTO serviceInfoDTO1 = new ServiceInfoDTO();
        serviceInfoDTO1.setId(1L);
        ServiceInfoDTO serviceInfoDTO2 = new ServiceInfoDTO();
        assertThat(serviceInfoDTO1).isNotEqualTo(serviceInfoDTO2);
        serviceInfoDTO2.setId(serviceInfoDTO1.getId());
        assertThat(serviceInfoDTO1).isEqualTo(serviceInfoDTO2);
        serviceInfoDTO2.setId(2L);
        assertThat(serviceInfoDTO1).isNotEqualTo(serviceInfoDTO2);
        serviceInfoDTO1.setId(null);
        assertThat(serviceInfoDTO1).isNotEqualTo(serviceInfoDTO2);
    }
}
