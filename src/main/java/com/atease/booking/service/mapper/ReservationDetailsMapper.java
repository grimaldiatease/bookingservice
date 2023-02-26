package com.atease.booking.service.mapper;

import com.atease.booking.domain.Reservation;
import com.atease.booking.domain.ReservationDetails;
import com.atease.booking.service.dto.ReservationDTO;
import com.atease.booking.service.dto.ReservationDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReservationDetails} and its DTO {@link ReservationDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReservationDetailsMapper extends EntityMapper<ReservationDetailsDTO, ReservationDetails> {
    @Mapping(target = "reservation", source = "reservation", qualifiedByName = "reservationId")
    ReservationDetailsDTO toDto(ReservationDetails s);

    @Named("reservationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ReservationDTO toDtoReservationId(Reservation reservation);
}
