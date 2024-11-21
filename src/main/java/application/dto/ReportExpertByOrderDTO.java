package application.dto;

import application.entity.enumeration.OrderStatus;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportExpertByOrderDTO implements Serializable {

    private Integer offerId;
    private Integer orderId;
    private OrderStatus orderStatus;
    private Boolean isDoneThisExpertId;

}
