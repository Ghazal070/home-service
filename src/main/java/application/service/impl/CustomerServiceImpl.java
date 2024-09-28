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


public class CustomerServiceImpl extends UserServiceImpl<CustomerRepository, Customer> implements CustomerService {

    private final DutyService dutyService;
    private final AuthHolder authHolder;
    private final OrderService orderService;

    public CustomerServiceImpl(CustomerRepository repository, AuthHolder authHolder, PasswordEncode passwordEncode, DutyService dutyService, AuthHolder authHolder1, OrderService orderService) {
        super(repository, authHolder, passwordEncode);
        this.dutyService = dutyService;
        this.authHolder = authHolder1;
        this.orderService = orderService;
    }


    @Override
    public Order orderSubmit(OrderSubmission orderSubmission) {
        //done get id for duty in dto
        if (orderSubmission != null) {
            Duty duty = dutyService.findById(orderSubmission.getId());
            Customer customer = repository.findById(authHolder.tokenId);
            if (duty != null && customer != null && duty.getSelectable()) {
                if (duty.getBasePrice() <= orderSubmission.getPriceOrder()) {
                    Order order = Order.builder()
                            .customer(customer)
                            .description(orderSubmission.getDescription())
                            .address(orderSubmission.getAddress())
                            .priceOrder(orderSubmission.getPriceOrder())
                            .duty(duty)
                            .orderStatus(OrderStatus.ExpertOfferWanting)
                            .dateTimeOrder(orderSubmission.getDateTimeOrder())
                            .build();
                    return orderService.save(order);
                } else throw new ValidationException("Duty base price greater than your order");
            } else
                throw new ValidationException("Title duty must be from Duty and be selectable List or auth holder is null");
        } else throw new ValidationException("OrderSubmission is null");
    }
}
