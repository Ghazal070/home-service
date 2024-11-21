package application.dto;

import application.annotation.ValidOrderStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchOrderDto {

    private LocalDate startDate;
    private LocalDate endDate;

    @ValidOrderStatus
    private String orderStatus;

    private Set<Integer> dutyId;
}