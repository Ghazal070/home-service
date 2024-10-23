package application.service;

import application.dto.OrderReportDto;
import application.entity.Order;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;

public interface OrderService  extends BaseEntityService<Order,Integer>{


    Set<Order> getOrdersForExpertWaitingOrChoosing(Integer expertId);
    Set<Order> findAllByCustomerId(Integer customerId);

}
