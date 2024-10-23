package application.service;

import application.dto.OrderReportDto;
import application.entity.users.Users;

import java.util.List;

public interface AdminReportByUser {
    List<OrderReportDto> getOrdersByUser(Integer userId,String userRole);
}
