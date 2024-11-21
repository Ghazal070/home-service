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
public class ReportCustomerByOrderDTO implements Serializable {

    private Integer orderId;
    private OrderStatus orderStatus;
    private Integer countOffers;

}
