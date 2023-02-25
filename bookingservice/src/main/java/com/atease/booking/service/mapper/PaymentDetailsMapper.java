package com.atease.booking.service.mapper;

import com.atease.booking.domain.Payment;
import com.atease.booking.domain.PaymentDetails;
import com.atease.booking.service.dto.PaymentDTO;
import com.atease.booking.service.dto.PaymentDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentDetails} and its DTO {@link PaymentDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentDetailsMapper extends EntityMapper<PaymentDetailsDTO, PaymentDetails> {
    @Mapping(target = "payment", source = "payment", qualifiedByName = "paymentId")
    PaymentDetailsDTO toDto(PaymentDetails s);

    @Named("paymentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PaymentDTO toDtoPaymentId(Payment payment);
}
