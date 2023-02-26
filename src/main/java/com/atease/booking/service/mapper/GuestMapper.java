package com.atease.booking.service.mapper;

import com.atease.booking.domain.Guest;
import com.atease.booking.service.dto.GuestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Guest} and its DTO {@link GuestDTO}.
 */
@Mapper(componentModel = "spring")
public interface GuestMapper extends EntityMapper<GuestDTO, Guest> {}
