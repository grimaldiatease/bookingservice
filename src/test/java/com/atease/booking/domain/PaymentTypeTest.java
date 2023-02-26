package com.atease.booking.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.atease.booking.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentType.class);
        PaymentType paymentType1 = new PaymentType();
        paymentType1.setId(1L);
        PaymentType paymentType2 = new PaymentType();
        paymentType2.setId(paymentType1.getId());
        assertThat(paymentType1).isEqualTo(paymentType2);
        paymentType2.setId(2L);
        assertThat(paymentType1).isNotEqualTo(paymentType2);
        paymentType1.setId(null);
        assertThat(paymentType1).isNotEqualTo(paymentType2);
    }
}
