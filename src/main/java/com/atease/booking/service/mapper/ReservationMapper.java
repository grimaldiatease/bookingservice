package com.atease.booking.service.mapper;

import com.atease.booking.domain.Guest;
import com.atease.booking.domain.Reservation;
import com.atease.booking.service.dto.GuestDTO;
import com.atease.booking.service.dto.ReservationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Reservation} and its DTO {@link ReservationDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReservationMapper extends EntityMapper<ReservationDTO, Reservation> {
    @Mapping(target = "guest", source = "guest", qualifiedByName = "guestId")
    ReservationDTO toDto(Reservation s);

    @Named("guestId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GuestDTO toDtoGuestId(Guest guest);
}
