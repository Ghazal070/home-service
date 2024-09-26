package application.repository.impl;

import application.entity.Credit;
import application.entity.Credit_;
import application.entity.Offer;
import application.entity.Offer_;
import application.repository.CreditRepository;
import application.repository.OfferRepository;
import jakarta.persistence.EntityManager;

public class OfferRepositoryImpl extends BaseEntityRepositoryImpl<Offer, Integer>
        implements OfferRepository {
    public OfferRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Offer> getEntityClass() {
        return Offer.class;
    }

    @Override
    public String getUniqueFieldName() {
        return Offer_.ID;
    }
}
