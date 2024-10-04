package application.service.impl;

import application.entity.Order;
import application.repository.OrderRepository;
import application.service.OrderService;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service

public class OrderServiceImpl extends BaseEntityServiceImpl<OrderRepository, Order, Integer> implements OrderService {
    public OrderServiceImpl(Validator validator, OrderRepository repository) {
        super(validator, repository);
    }

    @Transactional
    @Override
    public Set<Order> getOrdersForExpert(Integer expertId) {
        return repository.getOrdersForExpert(expertId);
    }
}
