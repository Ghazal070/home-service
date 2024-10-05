package application.service.impl;

import application.dto.OfferCreation;
import application.entity.Offer;
import application.entity.Order;
import application.entity.enumeration.ExpertStatus;
import application.entity.enumeration.OrderStatus;
import application.entity.users.Expert;
import application.exception.ValidationException;
import application.repository.ExpertRepository;
import application.service.ExpertService;
import application.service.OfferService;
import application.service.OrderService;
import application.service.PasswordEncodeService;
import application.util.AuthHolder;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ExpertServiceImpl extends UserServiceImpl<ExpertRepository, Expert> implements ExpertService {

    private final OrderService orderService;
    private final OfferService offerService;
    public ExpertServiceImpl(Validator validator, ExpertRepository repository, AuthHolder authHolder, PasswordEncodeService passwordEncodeService, OrderService orderService, OfferService offerService) {
        super(validator, repository, authHolder, passwordEncodeService);
        this.orderService = orderService;
        this.offerService = offerService;
    }

    @Override
    public Boolean havePermissionExpertToServices(Expert expert) {
        if (expert != null) {
            if (expert.getExpertStatus().equals(ExpertStatus.Accepted)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Offer sendOffer(OfferCreation offerCreation) {
        Optional<Order> order = orderService.findById(offerCreation.getOrderId());
        if (order.isPresent()){
            Set<Order> orderList = orderService.getOrdersForExpert(authHolder.getTokenId());
            boolean existOrderInList = false;
            for (Order orderTemp:orderList) {
                if (order.get().equals(orderTemp)){
                    existOrderInList=true;
                    break;
                }
            }
            Integer basePrice = order.get().getDuty().getBasePrice();
            if (!existOrderInList){
                throw new ValidationException("This order is not in list expert order or not waiting");
            }
            if (basePrice != null && basePrice <= offerCreation.getPriceOffer()){
                Optional<Expert> expert = repository.findById(authHolder.getTokenId());
                Offer offer = Offer.builder()
                        .order(order.get())
                        .expert(expert.get())
                        .dateTimeOffer(offerCreation.getDateTimeOffer())
                        .dateTimeStartWork(offerCreation.getDateTimeStartWork())
                        .lengthDays(offerCreation.getLengthDays())
                        .priceOffer(offerCreation.getPriceOffer())
                        .build();
                Offer saveOffer = offerService.save(offer);
                if (saveOffer!=null){
                    order.get().setOrderStatus(OrderStatus.ExpertChooseWanting);
                    orderService.update(order.get());
                    return saveOffer;
                }else throw new ValidationException("Error in saving offer");
            }else throw new ValidationException("Base price greater than your offer");
        } else throw new ValidationException("Order is null");
    }
}
