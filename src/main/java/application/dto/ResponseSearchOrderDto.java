package application.dto;


import application.entity.enumeration.OrderStatus;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseSearchOrderDto implements Serializable {

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

}