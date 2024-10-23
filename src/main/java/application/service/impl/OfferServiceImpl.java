package application.service.impl;

import application.dto.projection.OfferProjection;
import application.entity.Offer;
import application.repository.OfferRepository;
import application.service.OfferService;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class OfferServiceImpl extends BaseEntityServiceImpl<OfferRepository, Offer, Integer> implements OfferService {
    public OfferServiceImpl(Validator validator, OfferRepository repository) {
        super(validator, repository);
    }
    @Override
    @Transactional
    public Set<Offer> getOfferByCustomerIdOrderByScoreExpert(Integer customerId,Integer orderId){
        return repository.getOfferByCustomerIdOrderByScoreExpert(customerId,orderId);
    }

    @Override
    @Transactional
    public Set<Offer> getOfferByCustomerIdOrderByPriceOrder(Integer customerId, Integer orderId) {
        return repository.getOfferByCustomerIdOrderByPriceOrder(customerId,orderId);
    }

    @Override
    public Optional<Offer> findOfferByOrderId(Integer orderId) {
        return repository.findOfferByOrderId(orderId);
    }

    @Override
    public Set<Offer> findAllByExpertId(Integer expertId) {
        return repository.findAllByExpertId(expertId);
    }
}
