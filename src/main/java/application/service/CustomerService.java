package application.service;

import application.dto.OrderSubmission;
import application.entity.Order;
import application.entity.users.Customer;

public interface CustomerService extends UserService<Customer>{

    Order orderSubmit(OrderSubmission orderSubmission);
    boolean isCustomerAuthenticated();
}
