package application.service.impl;

import application.entity.Offer;
import application.repository.OfferRepository;
import application.service.OfferService;

public class OfferServiceImpl extends BaseEntityServiceImpl<OfferRepository, Offer, Integer> implements OfferService {

    public OfferServiceImpl(OfferRepository repository) {
        super(repository);
    }
}
