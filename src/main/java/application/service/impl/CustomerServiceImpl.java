package application.service.impl;

import application.dto.OrderSubmission;
import application.entity.Duty;
import application.entity.Order;
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
        if (orderSubmission != null){
            String dutyTitle = orderSubmission.getDutyTitle();
            Duty duty = dutyService.findByUniqId(dutyTitle);
            Customer customer = repository.findById(authHolder.tokenId);
            if(duty!=null && customer !=null){
                if (duty.getSelectable()){
                    Order order = Order.builder()
                            .customer(customer)
                            .description(orderSubmission.getDescription())
                            .address(orderSubmission.getAddress())
                            .priceOrder(orderSubmission.getPriceOrder())
                            .duty(duty)
                            .dateTimeOrder(orderSubmission.getDateTimeOrder())
                            .build();
                    return orderService.save(order);
                }else throw new ValidationException("Duty is not selectable");
            }else throw new ValidationException("Title duty must be from Duty List or auth holder is null");

        }else throw  new ValidationException("OrderSubmission is null");
    }
}
