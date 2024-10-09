package application.dto;


import application.entity.Offer;

import java.util.Set;

public record OrderExpertWaitingDto(
        String description,
        Integer priceOrder,
        String dateTimeOrder,
        String orderStatus,
        Integer customerId,
        Integer expertId,
        Integer dutyId
) {
}
