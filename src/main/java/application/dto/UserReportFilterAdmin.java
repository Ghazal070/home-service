package application.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReportFilterAdmin implements Serializable {


    private String registerDateStart;
    private String registerDateEnd;
    private Integer minTotalRequests;
    private Integer maxTotalRequests;
    private Integer minDoneOrders;
    private Integer maxDoneOrders;
}
