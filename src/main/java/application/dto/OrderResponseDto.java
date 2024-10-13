package application.dto;


import application.entity.Offer;
import java.util.Set;

public record OrderResponseDto(
        String description,
        Integer priceOrder,
        String dateTimeOrderForDoingFromCustomer,
        String orderStatus,
        Integer customerId,
        Integer expertId,
        Integer dutyId,
        Set<Offer> offers
) {
}
