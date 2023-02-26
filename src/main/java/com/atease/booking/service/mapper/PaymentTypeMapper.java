package com.atease.booking.service.mapper;

import com.atease.booking.domain.Payment;
import com.atease.booking.domain.PaymentType;
import com.atease.booking.service.dto.PaymentDTO;
import com.atease.booking.service.dto.PaymentTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentType} and its DTO {@link PaymentTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentTypeMapper extends EntityMapper<PaymentTypeDTO, PaymentType> {
    @Mapping(target = "payment", source = "payment", qualifiedByName = "paymentId")
    PaymentTypeDTO toDto(PaymentType s);

    @Named("paymentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PaymentDTO toDtoPaymentId(Payment payment);
}
