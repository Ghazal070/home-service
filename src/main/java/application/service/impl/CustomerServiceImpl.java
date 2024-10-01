package application.service.impl;

import application.dto.OrderSubmission;
import application.entity.Duty;
import application.entity.Order;
import application.entity.enumeration.OrderStatus;
import application.entity.users.Customer;
import application.repository.CustomerRepository;
import application.service.CustomerService;
import application.service.DutyService;
import application.service.OrderService;
import application.service.PasswordEncode;
import application.util.AuthHolder;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl extends UserServiceImpl<CustomerRepository, Customer> implements CustomerService {

    private final DutyService dutyService;
    private final OrderService orderService;

    public CustomerServiceImpl(Validator validator, CustomerRepository repository, AuthHolder authHolder, PasswordEncode passwordEncode, DutyService dutyService, OrderService orderService) {
        super(validator, repository, authHolder, passwordEncode);
        this.dutyService = dutyService;
        this.orderService = orderService;
    }

    @Override
    public Order orderSubmit(OrderSubmission orderSubmission) {
        if (!isCustomerAuthenticated()) {
            throw new ValidationException("Customer must be logged in to view duties.");
        }
        //done get id for duty in dto
        if (orderSubmission != null) {
            Optional<Duty> duty = dutyService.findById(orderSubmission.getId());
            Optional<Customer> customer = repository.findById(authHolder.tokenId);
            if (duty.isPresent() && customer.isPresent() && duty.get().getSelectable()) {
                if (duty.get().getBasePrice() <= orderSubmission.getPriceOrder()) {
                    Order order = Order.builder()
                            .customer(customer.get())
                            .description(orderSubmission.getDescription())
                            .address(orderSubmission.getAddress())
                            .priceOrder(orderSubmission.getPriceOrder())
                            .duty(duty.get())
                            .orderStatus(OrderStatus.ExpertOfferWanting)
                            .dateTimeOrder(orderSubmission.getDateTimeOrder())
                            .build();
                    return orderService.save(order);
                } else throw new ValidationException("Duty base price greater than your order");
            } else
                throw new ValidationException("Title duty must be from Duty and be selectable List or auth holder is null");
        } else throw new ValidationException("OrderSubmission is null");
    }
    public boolean isCustomerAuthenticated() {
        if (authHolder != null && authHolder.tokenId != null){
            Optional<Customer> customer = repository.findById(authHolder.tokenId);
            if (customer.isEmpty()){
                throw new ValidationException("Login user is not customer");
            }
            return true;
        }else throw new ValidationException("Auth holder is null");
    }

}
