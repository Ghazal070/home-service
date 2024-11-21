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
public class OrderReportDto implements Serializable {

    private Integer orderId;
    private OrderStatus orderStatus;
    private Integer priceOrder;
    private Integer priceOffer;
    private LocalDateTime orderDateCreation;
    private LocalDateTime dateForDoingFromCustomer;
    private LocalDateTime dateTimeStartOffer;
    private LocalDateTime dateTimeEndOffer;
    private LocalDateTime dateDone;
    private Long delayExpertHours;
    private Integer expertId;
    private Integer dutyId;
    private Integer countOffer;
    private Integer acceptOfferId;

}
