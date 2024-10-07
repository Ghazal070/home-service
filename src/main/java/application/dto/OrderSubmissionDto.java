package application.dto;

import application.mapper.CustomLocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderSubmissionDto  implements Serializable {

    @NotNull(message = "address order must not be null")
    private Integer dutyId;
    @NotNull(message = "order description must not be null")
    private String description;

    @NotNull(message = "priceOrder description must not be null")
    private Integer priceOrder;

    @NotNull(message = "dateTimeOrder must not be null")
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime dateTimeOrder;

    @NotNull(message = "address order must not be null")
    private String address;



}
