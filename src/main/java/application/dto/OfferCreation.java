package application.dto;

import application.entity.Order;
import application.entity.users.Expert;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfferCreation {

    @NotNull
    private Integer orderId;

    @NotNull
    private Integer priceOffer;

    @NotNull
    private LocalDateTime dateTimeOffer;

    @Future
    @NotNull
    private LocalDateTime dateTimeStartWork;

    @Min(value = 1 , message = "Min days for days working is 1")
    @NotNull
    private Integer lengthDays;
}
