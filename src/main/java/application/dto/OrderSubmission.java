package application.dto;

import application.entity.Duty;
import application.entity.Offer;
import application.entity.enumeration.OrderStatus;
import application.entity.users.Customer;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderSubmission {

    @NotNull(message = "order description must not be null")
    private String description;

    @NotNull(message = "priceOrder description must not be null")
    private Integer priceOrder;

    @NotNull(message = "dateTimeOrder must not be null")
    private ZonedDateTime dateTimeOrder;

    @NotNull(message = "address order must not be null")
    private String address;

    private String dutyTitle;

}
