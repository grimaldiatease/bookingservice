package com.atease.booking.service.mapper;

import com.atease.booking.domain.Reservation;
import com.atease.booking.domain.ServiceInfo;
import com.atease.booking.service.dto.ReservationDTO;
import com.atease.booking.service.dto.ServiceInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceInfo} and its DTO {@link ServiceInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ServiceInfoMapper extends EntityMapper<ServiceInfoDTO, ServiceInfo> {
    @Mapping(target = "reservation", source = "reservation", qualifiedByName = "reservationId")
    ServiceInfoDTO toDto(ServiceInfo s);

    @Named("reservationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ReservationDTO toDtoReservationId(Reservation reservation);
}
