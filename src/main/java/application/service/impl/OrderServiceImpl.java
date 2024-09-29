package application.service.impl;

import application.entity.Order;
import application.repository.OrderRepository;
import application.service.OrderService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends BaseEntityServiceImpl<OrderRepository, Order, Integer> implements OrderService {
    public OrderServiceImpl(Validator validator, OrderRepository repository) {
        super(validator, repository);
    }
}
