package repository.impl;

import entity.users.Customer;
import jakarta.persistence.EntityManager;

public class CustomerRepositoryImpl extends UserRepositoryImpl<Customer> {

    public CustomerRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Customer> getEntityClass() {
        return Customer.class;
    }

    @Override
    public String getUniqueFieldName() {
        return null;
    }
}
