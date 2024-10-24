package application.service;

import application.dto.projection.OfferProjection;
import application.entity.Offer;
import application.entity.Order;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface OfferService extends BaseEntityService<Offer,Integer>{
    Set<Offer> getOfferByCustomerIdOrderByScoreExpert(Integer customerId,Integer orderId);
    Set<Offer> getOfferByCustomerIdOrderByPriceOrder(Integer customerId, Integer orderId);
    Optional<Offer> findOfferByOrderId(Integer orderId);
    Set<Offer> findAllByExpertId(Integer expertId);



}
