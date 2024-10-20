package application.dto;

import lombok.*;
import java.io.Serializable;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfferResponseDto implements Serializable {

    private Integer id;
    private Integer orderId;

    private Integer expertId;

    private Integer priceOffer;

    private String dateTimeOffer;

    private String dateTimeStartWork;
    private Integer lengthDays;

}
