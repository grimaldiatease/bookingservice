package com.atease.booking.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.atease.booking.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReservationDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReservationDetails.class);
        ReservationDetails reservationDetails1 = new ReservationDetails();
        reservationDetails1.setId(1L);
        ReservationDetails reservationDetails2 = new ReservationDetails();
        reservationDetails2.setId(reservationDetails1.getId());
        assertThat(reservationDetails1).isEqualTo(reservationDetails2);
        reservationDetails2.setId(2L);
        assertThat(reservationDetails1).isNotEqualTo(reservationDetails2);
        reservationDetails1.setId(null);
        assertThat(reservationDetails1).isNotEqualTo(reservationDetails2);
    }
}
