package com.atease.booking.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.atease.booking.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReservationDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReservationDetailsDTO.class);
        ReservationDetailsDTO reservationDetailsDTO1 = new ReservationDetailsDTO();
        reservationDetailsDTO1.setId(1L);
        ReservationDetailsDTO reservationDetailsDTO2 = new ReservationDetailsDTO();
        assertThat(reservationDetailsDTO1).isNotEqualTo(reservationDetailsDTO2);
        reservationDetailsDTO2.setId(reservationDetailsDTO1.getId());
        assertThat(reservationDetailsDTO1).isEqualTo(reservationDetailsDTO2);
        reservationDetailsDTO2.setId(2L);
        assertThat(reservationDetailsDTO1).isNotEqualTo(reservationDetailsDTO2);
        reservationDetailsDTO1.setId(null);
        assertThat(reservationDetailsDTO1).isNotEqualTo(reservationDetailsDTO2);
    }
}
