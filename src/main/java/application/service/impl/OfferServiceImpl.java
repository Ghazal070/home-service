package application.service.impl;

import application.entity.Offer;
import application.repository.OfferRepository;
import application.service.OfferService;
import jakarta.validation.Validator;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OfferServiceImpl extends BaseEntityServiceImpl<OfferRepository, Offer, Integer> implements OfferService {
    public OfferServiceImpl(Validator validator, OfferRepository repository) {
        super(validator, repository);
    }
    @Override
    public Set<Offer> getOfferByCustomerIdOrderByScoreExpert(Integer customerId,Integer orderId){
        return repository.getOfferByCustomerIdOrderByScoreExpert(customerId,orderId);
    }

    @Override
    public Set<Offer> getOfferByCustomerIdOrderByPriceOrder(Integer customerId,Integer orderId) {
        return repository.getOfferByCustomerIdOrderByPriceOrder(customerId,orderId);
    }

    @Override
    public Offer updateByOrderStatus(String orderStatus, Integer offerId) {
        return repository.updateByOrderStatus(orderStatus,offerId);
    }
}
