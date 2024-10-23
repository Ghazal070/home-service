package application.dto;


import application.entity.enumeration.OrderStatus;
import lombok.*;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseSearchOrderDto {

    private Integer orderId;
    private Integer priceOrder;
    private LocalDateTime orderDateCreation;
    private LocalDateTime dateTimeOrderForDoingFromCustomer;
    private OrderStatus orderStatus;
    private Integer customerId;
    private Integer expertId;
    private Integer dutyId;
    private Integer countOffers;
    private LocalDateTime doneUpdate;

    @Override
    public String toString() {
        return "ResponseSearchOrderDto{" +
                "orderId=" + orderId +
                ", priceOrder=" + priceOrder +
                ", orderDateCreation=" + orderDateCreation +
                ", dateTimeOrderForDoingFromCustomer=" + dateTimeOrderForDoingFromCustomer +
                ", orderStatus=" + orderStatus +
                ", customerId=" + customerId +
                ", expertId=" + expertId +
                ", dutyId=" + dutyId +
                ", countOffers=" + countOffers +
                ", doneUpdate=" + doneUpdate +
                '}';
    }
}