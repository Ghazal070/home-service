package application.service.impl;

import application.dto.CardDto;
import application.dto.OrderSubmissionDto;
import application.entity.*;
import application.entity.enumeration.OrderStatus;
import application.entity.users.Customer;
import application.entity.users.Expert;
import application.repository.CustomerRepository;
import application.service.*;
import application.util.AuthHolder;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomerServiceImpl extends UserServiceImpl<CustomerRepository, Customer> implements CustomerService {

    private final DutyService dutyService;
    private final OrderService orderService;
    private final OfferService offerService;
    private final CreditService creditService;
    private final CardService cardService;
    private final ExpertService expertService;

    public CustomerServiceImpl(Validator validator, CustomerRepository repository, AuthHolder authHolder, PasswordEncodeService passwordEncodeService, DutyService dutyService, OrderService orderService, OfferService offerService, CreditService creditService, CardService cardService, ExpertService expertService) {
        super(validator, repository, authHolder, passwordEncodeService);
        this.dutyService = dutyService;
        this.orderService = orderService;
        this.offerService = offerService;
        this.creditService = creditService;
        this.cardService = cardService;
        this.expertService = expertService;
    }

    @Override
    public Order orderSubmit(OrderSubmissionDto orderSubmissionDto, Integer customerId) {
        Optional<Customer> customer = isCustomerAuthenticated(customerId);
        if (customer.isEmpty()) {
            throw new ValidationException("Customer must be logged in to view duties.");
        }
        if (orderSubmissionDto != null) {
            Optional<Duty> duty = dutyService.findById(orderSubmissionDto.getDutyId());
            if (duty.isPresent() && duty.get().getSelectable()) {
                if (duty.get().getBasePrice() != null && duty.get().getBasePrice() <= orderSubmissionDto.getPriceOrder()) {
                    Order order = Order.builder()
                            .customer(customer.get())
                            .description(orderSubmissionDto.getDescription())
                            .address(orderSubmissionDto.getAddress())
                            .priceOrder(orderSubmissionDto.getPriceOrder())
                            .duty(duty.get())
                            .orderStatus(OrderStatus.ExpertOfferWanting)
                            .dateTimeOrderForDoingFromCustomer(orderSubmissionDto.getDateTimeOrderForDoingFromCustomer())
                            .build();
                    return orderService.save(order);
                } else throw new ValidationException("Duty base price greater than your order");
            } else
                throw new ValidationException("Title duty must be from Duty and be selectable List or auth holder is null");
        } else throw new ValidationException("OrderSubmission is null");
    }

    public Optional<Customer> isCustomerAuthenticated(Integer customerId) {
        return repository.findById(customerId);
    }

    @Override
    public Set<Offer> getOffersForOrder(Integer orderId, Integer customerId) {
        return offerService.getOfferByCustomerIdOrderByPriceOrder(customerId, orderId);
    }

    @Override
    public Boolean chooseExpertForOrder(Integer offerId) {
        Optional<Offer> optionalOffer = offerService.findById(offerId);
        if (optionalOffer.isPresent()) {
            Offer offer = optionalOffer.get();
            Order order = offer.getOrder();
            if (order.getOrderStatus().equals(OrderStatus.ExpertChooseWanting)) {
                order.setOrderStatus(OrderStatus.ComingToLocationWanting);
                order.setExpert(offer.getExpert());
                orderService.update(order);
                return true;
            } else throw new ValidationException("Order status is not ExpertChooseWanting");
        } else throw new ValidationException("Offer is null");
    }

    @Override
    public Boolean orderStarted(Integer offerId) {
        Optional<Offer> optionalOffer = offerService.findById(offerId);
        if (optionalOffer.isPresent()) {
            Offer offer = optionalOffer.get();
            Order order = offer.getOrder();
            if (order.getOrderStatus().equals(OrderStatus.ComingToLocationWanting)) {
                if (LocalDateTime.now().isAfter(offer.getDateTimeStartWork())) {
                    order.setOrderStatus(OrderStatus.Started);
                    orderService.update(order);
                    return true;
                } else throw new ValidationException("DateTime is not After DateTimeStartWork offer");
            } else throw new ValidationException("Order status is not ComingToLocationWanting");
        } else throw new ValidationException("Order is null");
    }

    @Override
    public Boolean orderDone(Integer offerId) {
        Optional<Offer> optionalOffer = offerService.findById(offerId);
        if (optionalOffer.isPresent()) {
            Offer offer = optionalOffer.get();
            Order order = offer.getOrder();
            if (order.getOrderStatus().equals(OrderStatus.Started)) {
                order.setOrderStatus(OrderStatus.Done);
                order.setDoneUpdate(LocalDateTime.now());
                orderService.update(order);
                return true;
            } else throw new ValidationException("Order status is not Started");
        } else throw new ValidationException("Order is null");
    }

    @Override
    public void payment(String paymentType, Integer offerId, Integer customerId,CardDto cardDto) {
        Optional<Customer> customerOptional = repository.findById(customerId);
        if (customerOptional.isEmpty()) {
            throw new ValidationException("Customer id is not exist");
        }
        Optional<Offer> offerOptional = offerService.findById(offerId);
        if (offerOptional.isEmpty()) {
            throw new ValidationException("This offerId is not exist");
        }
        Offer offer = offerOptional.get();
        Order order = offer.getOrder();
        if (!order.getOrderStatus().equals(OrderStatus.Done)) {
            throw new ValidationException("OrderStatus for this offerId is not done ");
        }
        Customer customer = customerOptional.get();
        switch (paymentType) {
            case "CreditPayment" ->
                creditPayment(offer,customer);
            case "AccountPayment" ->
                accountPayment(cardDto,customer,offer);
            default -> throw new ValidationException(
                    "Please choose CreditPayment or AccountPayment");
        }
        order.setOrderStatus(OrderStatus.Payed);
        Expert expert = order.getExpert();
        offerService.update(offer);
        LocalDateTime expectedDateTime = offer.getDateTimeStartWork().plusDays(offer.getLengthDays());
        LocalDateTime realDateTime = order.getDoneUpdate();
        Duration duration = Duration.between(realDateTime, expectedDateTime);
        long hour = duration.toHours();
        if (hour > 0){
            expert.setScore(expert.getScore() - (int) hour);
            if (expert.getScore()<0){
                expert.setIsActive(false);
            }
        }
    }

    public void creditPayment(Offer offer, Customer customer){
        Credit credit = customer.getCredit();
        if (credit.getAmount() < offer.getPriceOffer()) {
            throw new ValidationException("Your credit lower than offer amount");
        }
        credit.setAmount(credit.getAmount() - offer.getPriceOffer());
        creditService.update(credit);
    }
    public void accountPayment(CardDto cardDto, Customer customer,Offer offer){
        Card existingCard = cardService.validateCard(cardDto);
        existingCard.setAmountCard(existingCard.getAmountCard() - offer.getPriceOffer());
        cardService.update(existingCard);
        Credit expertCredit = offer.getExpert().getCredit();
        expertCredit.setAmount((int) (expertCredit.getAmount() +  (0.7 * offer.getPriceOffer())));
    }


}
