package com.atease.booking.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.atease.booking.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentDetailsDTO.class);
        PaymentDetailsDTO paymentDetailsDTO1 = new PaymentDetailsDTO();
        paymentDetailsDTO1.setId(1L);
        PaymentDetailsDTO paymentDetailsDTO2 = new PaymentDetailsDTO();
        assertThat(paymentDetailsDTO1).isNotEqualTo(paymentDetailsDTO2);
        paymentDetailsDTO2.setId(paymentDetailsDTO1.getId());
        assertThat(paymentDetailsDTO1).isEqualTo(paymentDetailsDTO2);
        paymentDetailsDTO2.setId(2L);
        assertThat(paymentDetailsDTO1).isNotEqualTo(paymentDetailsDTO2);
        paymentDetailsDTO1.setId(null);
        assertThat(paymentDetailsDTO1).isNotEqualTo(paymentDetailsDTO2);
    }
}
