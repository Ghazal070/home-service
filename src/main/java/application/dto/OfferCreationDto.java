package application.dto;

import application.mapper.CustomLocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfferCreationDto  implements Serializable {

    @NotNull
    private Integer orderId;

    @NotNull
    private Integer priceOffer;

    @Future
    @NotNull
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime dateTimeStartWork;

    @Min(value = 1 , message = "Min days for days working is 1")
    @NotNull
    private Integer lengthDays;

}
