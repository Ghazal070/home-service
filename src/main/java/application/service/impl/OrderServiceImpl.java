package application.service.impl;

import application.entity.Order;
import application.repository.OrderRepository;
import application.service.OrderService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class OrderServiceImpl extends BaseEntityServiceImpl<OrderRepository, Order, Integer> implements OrderService {


    public OrderServiceImpl(Validator validator, OrderRepository repository) {
        super(validator, repository);
    }

    @Override
    @Transactional
    public Set<Order> getOrdersForExpertWaitingOrChoosing(Integer expertId) {
        return repository.getOrdersForExpertWaitingOrChoosing(expertId);
    }

    @Override
    public Set<Order> findAllByCustomerId(Integer customerId) {
        return repository.findAllByCustomerId(customerId);
    }

}
