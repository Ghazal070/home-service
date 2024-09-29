package application.service.impl;

import application.entity.Offer;
import application.repository.OfferRepository;
import application.service.OfferService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

@Service
public class OfferServiceImpl extends BaseEntityServiceImpl<OfferRepository, Offer, Integer> implements OfferService {
    public OfferServiceImpl(Validator validator, OfferRepository repository) {
        super(validator, repository);
    }
}
