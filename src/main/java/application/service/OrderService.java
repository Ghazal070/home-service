package application.service;

import application.entity.Order;

import java.util.List;
import java.util.Set;

public interface OrderService  extends BaseEntityService<Order,Integer>{


    Set<Order> getOrdersForExpertWaitingOrChoosing(Integer expertId);
}
