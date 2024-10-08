package application.service;

import application.dto.projection.OfferProjection;
import application.entity.Offer;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface OfferService extends BaseEntityService<Offer,Integer>{
    Set<Offer> getOfferByCustomerIdOrderByScoreExpert(Integer customerId,Integer orderId);
    Set<Offer> getOfferByCustomerIdOrderByPriceOrder(Integer customerId, Integer orderId);


}
