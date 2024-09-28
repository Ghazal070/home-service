package application.repository.impl;

import application.entity.Offer;
import application.entity.Offer_;
import application.entity.Order;
import application.entity.Order_;
import application.repository.DatabaseAccess;
import application.repository.OfferRepository;
import application.repository.OrderRepository;
import jakarta.persistence.EntityManager;

public class OrderRepositoryImpl extends BaseEntityRepositoryImpl<Order, Integer>
        implements OrderRepository {
    public OrderRepositoryImpl(DatabaseAccess<Order, Integer> databaseAccess) {
        super(databaseAccess);
    }

    @Override
    public Class<Order> getEntityClass() {
        return Order.class;
    }

    @Override
    public String getUniqueFieldName() {
        return Order_.ID;
    }
}
