package application.dto;


import application.entity.Offer;

import java.util.Set;

public record OrderExpertWaitingDto(
        Integer orderId,
        String description,
        Integer priceOrder,
        String dateTimeOrderForDoingFromCustomer,
        String orderStatus,
        Integer customerId,
        Integer expertId,
        Integer dutyId
) {
}
