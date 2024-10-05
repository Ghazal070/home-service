package application.dto;

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
public class OfferCreationDto {

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

    //OfferCreationDTO
}
