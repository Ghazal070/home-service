package application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderSubmissionDto {

    @NotNull(message = "address order must not be null")
    private Integer dutyId;
    @NotNull(message = "order description must not be null")
    private String description;

    @NotNull(message = "priceOrder description must not be null")
    private Integer priceOrder;

    @NotNull(message = "dateTimeOrder must not be null")
    private LocalDateTime dateTimeOrder;

    @NotNull(message = "address order must not be null")
    private String address;



}
