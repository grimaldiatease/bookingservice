package com.atease.booking.service.mapper;

import com.atease.booking.domain.Payment;
import com.atease.booking.domain.Reservation;
import com.atease.booking.service.dto.PaymentDTO;
import com.atease.booking.service.dto.ReservationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Payment} and its DTO {@link PaymentDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentMapper extends EntityMapper<PaymentDTO, Payment> {
    @Mapping(target = "reservation", source = "reservation", qualifiedByName = "reservationId")
    PaymentDTO toDto(Payment s);

    @Named("reservationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ReservationDTO toDtoReservationId(Reservation reservation);
}
