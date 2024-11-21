package application.service;

import application.dto.CardDto;
import application.dto.OrderSubmissionDto;
import application.dto.ReportCustomerByOrderDTO;
import application.dto.projection.UserOrderCount;
import application.entity.Invoice;
import application.entity.Offer;
import application.entity.Order;
import application.entity.users.Customer;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CustomerService extends UserService<Customer>{

    Order orderSubmit(OrderSubmissionDto orderSubmissionDto,Integer customerId);
    Optional<Customer> isCustomerAuthenticated(Integer customerId);
    Set<Offer> getOffersForOrder(Integer orderId,Integer customerId);
    Boolean chooseExpertForOrder(Integer offerId);
    Boolean orderStarted(Integer offerId);
    Boolean orderDone(Integer offerId,String commentContent,Integer score);

    //void payment(String paymentType, Integer offerId, Integer customerId,CardDto cardDto);

    Invoice creditPayment(Integer offerId, Integer customerId,String paymentType);
    Invoice accountPayment(Integer offerId, Integer customerId, String paymentType, CardDto cardDto);
    void putStartTimeForCaptcha(Integer customerId);
    Boolean validateVerificationToken(String token);
    String sendVerificationToken(String email);
    List<UserOrderCount> getCustomerOrderCounts();
    Set<ReportCustomerByOrderDTO> getCustomerOrders(Integer customerId);
    Integer getCreditFindByCustomerId(Integer customerId);

}
