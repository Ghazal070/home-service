package application.service.impl;

import application.dto.OrderReportDto;
import application.entity.Offer;
import application.entity.Order;
import application.service.*;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminReportByUserImpl implements AdminReportByUser {

    private final ExpertService expertService;
    private final CustomerService customerService;
    private final OrderService orderService;
    private final OfferService offerService;


    @Override
    public List<OrderReportDto> getOrdersByUser(Integer userId, String userRole) {
        List<OrderReportDto> reportDto = new ArrayList<>();

        switch (userRole) {
            case "Customer" -> {
                Set<Order> orders = getCustomerOrders(userId);
                orders.forEach(
                        order -> {
                            Offer offer = offerService.findOfferByOrderId(order.getId()).orElse(null);
                            orderToOrderReportDto(reportDto, order, offer);
                        }
                );
            }
            case "Expert" -> {
                Set<Offer> offers = getExpertOffers(userId);
                Set<Order> orders = getExpertOrders(offers);
                orders.forEach(
                        order -> {
                            Offer offer = null;
                            for (Offer offerTemp : offers) {
                                if (offerTemp.getOrder().getId().equals(order.getId())) {
                                    offer = offerTemp;
                                    break;
                                }
                            }
                            orderToOrderReportDto(reportDto, order, offer);
                        }
                );
            }
            default -> throw new ValidationException("UserRole must be Customer or Expert");
        }
        return reportDto;
    }

    private Set<Offer> getExpertOffers(Integer userId) {
        if (!expertService.existsById(userId)) {
            throw new ValidationException("UserId with this role not exists.");
        }
        return offerService.findAllByExpertId(userId);
    }

    private Set<Order> getExpertOrders(Set<Offer> offers) {
        Set<Order> orders = offers.stream().map(Offer::getOrder).collect(Collectors.toSet());
        if (orders.isEmpty()) {
            throw new ValidationException("OrdersList is empty");
        }
        return orders;
    }

    private Set<Order> getCustomerOrders(Integer userId) {
        if (!customerService.existsById(userId)) {
            throw new ValidationException("UserId with this role not exists.");
        }
        Set<Order> orders = orderService.findAllByCustomerId(userId);
        if (orders == null || orders.isEmpty()) {
            throw new ValidationException("OrdersList is empty");
        }
        return orders;
    }

    private void orderToOrderReportDto(List<OrderReportDto> reportDto, Order order, Offer offer) {
        Duration duration = (offer != null && order.getDoneUpdate() != null) ?
                (Duration.between(offer.getDateTimeStartWork().plus(offer.getLengthDays(), ChronoUnit.DAYS), order.getDoneUpdate()))
                : null;
        reportDto.add(
                OrderReportDto.builder()
                        .orderId(order.getId())
                        .orderStatus(order.getOrderStatus())
                        .priceOrder(order.getPriceOrder())
                        .priceOffer(offer != null ? offer.getPriceOffer() : null)
                        .orderDateCreation(order.getOrderDateCreation())
                        .dateForDoingFromCustomer(order.getDateTimeOrderForDoingFromCustomer())
                        .dateTimeStartOffer(offer != null ? offer.getDateTimeStartWork() : null)
                        .dateTimeEndOffer(offer != null ? offer.getDateTimeStartWork().plus(offer.getLengthDays(), ChronoUnit.DAYS) : null)
                        .expertId(order.getExpert() != null ? order.getExpert().getId() : null)
                        .dateDone(order.getDoneUpdate())
                        .delayExpertHours(duration != null ? duration.toHours() : 0L)
                        .dutyId(order.getDuty().getId())
                        .countOffer(order.getOffers().size())
                        .acceptOfferId(offer != null ? offer.getId() : null)
                        .build());
    }
}
