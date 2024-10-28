package application.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderCountReportDto {

    private Integer userId;
    private String role;
    private String email;
    private LocalDateTime registerDate;
    private Long totalRequests;
    private Long doneOrders;

    @Override
    public String toString() {
        return "UserOrderCountReportDto{" +
                "userId=" + userId +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", registerDate=" + registerDate +
                ", totalRequests=" + totalRequests +
                ", doneOrders=" + doneOrders +
                '}';
    }
}
