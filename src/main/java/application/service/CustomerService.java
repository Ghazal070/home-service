package application.service;

import application.dto.OrderSubmissionDto;
import application.entity.Offer;
import application.entity.Order;
import application.entity.users.Customer;

import java.util.Optional;
import java.util.Set;

public interface CustomerService extends UserService<Customer>{

    Order orderSubmit(OrderSubmissionDto orderSubmissionDto,Integer customerId);
    Optional<Customer> isCustomerAuthenticated(Integer customerId);
    Set<Offer> getOffersForOrder(Integer orderId,Integer customerId);
    Boolean chooseExpertForOrder(Integer offerId);
    Boolean orderStarted(Integer offerId);
    Boolean orderDone(Integer offerId);

}
