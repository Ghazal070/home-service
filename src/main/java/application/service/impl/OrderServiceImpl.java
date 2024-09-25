package application.service.impl;

import application.entity.Duty;
import application.entity.Order;
import application.repository.DutyRepository;
import application.repository.OrderRepository;
import application.service.DutyService;
import application.service.OrderService;

public class OrderServiceImpl extends BaseEntityServiceImpl<OrderRepository, Order, Integer> implements OrderService {

    public OrderServiceImpl(OrderRepository repository) {
        super(repository);
    }
}
