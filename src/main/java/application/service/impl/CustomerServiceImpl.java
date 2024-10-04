package application.service.impl;

import application.dto.OrderSubmission;
import application.entity.Duty;
import application.entity.Offer;
import application.entity.Order;
import application.entity.enumeration.OrderStatus;
import application.entity.users.Customer;
import application.repository.CustomerRepository;
import application.service.*;
import application.util.AuthHolder;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomerServiceImpl extends UserServiceImpl<CustomerRepository, Customer> implements CustomerService {

    private final DutyService dutyService;
    private final OrderService orderService;
    private final OfferService offerService;

    public CustomerServiceImpl(Validator validator, CustomerRepository repository, AuthHolder authHolder, PasswordEncode passwordEncode, DutyService dutyService, OrderService orderService, OfferService offerService) {
        super(validator, repository, authHolder, passwordEncode);
        this.dutyService = dutyService;
        this.orderService = orderService;
        this.offerService = offerService;
    }

    @Override
    public Order orderSubmit(OrderSubmission orderSubmission) {
        Optional<Customer> customer = isCustomerAuthenticated();
        if (customer.isEmpty()) {
            throw new ValidationException("Customer must be logged in to view duties.");
        }
        //done get id for duty in dto
        if (orderSubmission != null) {
            Optional<Duty> duty = dutyService.findById(orderSubmission.getDutyId());
            if (duty.isPresent() && duty.get().getSelectable()) {
                if (duty.get().getBasePrice()!=null && duty.get().getBasePrice() <= orderSubmission.getPriceOrder()) {
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

    public Optional<Customer> isCustomerAuthenticated() {
        Integer customerId = authHolder.getTokenId();
        return repository.findById(customerId);
    }

    @Override
    public Set<Offer> getOffersForOrder(Integer orderId) {
        return offerService.getOfferByCustomerIdOrderByPriceOrder(authHolder.getTokenId(), orderId);
    }

    @Override
    public Boolean chooseExpertForOrder(Integer offerId) {
        Optional<Offer> optionalOffer = offerService.findById(offerId);
        if (optionalOffer.isPresent()){
            Offer offer = optionalOffer.get();
            Order order = offer.getOrder();
            if (order.getOrderStatus().equals(OrderStatus.ExpertChooseWanting)){
                order.setOrderStatus(OrderStatus.ComingToLocationWanting);
                order.setExpert(offer.getExpert());
                orderService.update(order);
                return true;
            }else throw new ValidationException("Order status is not ExpertChooseWanting");
        }else throw new ValidationException("Offer is null");
    }

    @Override
    public Boolean orderStarted(Integer offerId) {
        Optional<Offer> optionalOffer = offerService.findById(offerId);
        if (optionalOffer.isPresent()){
            Offer offer = optionalOffer.get();
            Order order = offer.getOrder();
            if (order.getOrderStatus().equals(OrderStatus.ComingToLocationWanting)){
                if (LocalDateTime.now().isAfter(offer.getDateTimeStartWork())){
                    order.setOrderStatus(OrderStatus.Started);
                    orderService.update(order);
                    return true;
                }else throw new ValidationException("DateTime is not After DateTimeStartWork offer");
            }else throw new ValidationException("Order status is not ComingToLocationWanting");
        }else throw new ValidationException("Order is null");
    }
    @Override
    public Boolean orderDone(Integer offerId) {
        Optional<Offer> optionalOffer = offerService.findById(offerId);
        if (optionalOffer.isPresent()){
            Offer offer = optionalOffer.get();
            Order order = offer.getOrder();
            if (order.getOrderStatus().equals(OrderStatus.Started)){
                    order.setOrderStatus(OrderStatus.Done);
                    orderService.update(order);
                    return true;
            }else throw new ValidationException("Order status is not Started");
        }else throw new ValidationException("Order is null");
    }
}
