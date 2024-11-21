package application.dto.projection;

import java.time.LocalDateTime;

public interface UserOrderCount {
    Integer getUserId();
    String getRole();
    LocalDateTime getRegisterDate();
    Long getTotalRequests();
    Long getDoneOrders();
}
