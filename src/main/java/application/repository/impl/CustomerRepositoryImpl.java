package application.repository.impl;

import application.entity.users.Customer;
import application.entity.users.Profile_;
import jakarta.persistence.EntityManager;
import application.repository.CustomerRepository;

public class CustomerRepositoryImpl extends UserRepositoryImpl<Customer> implements CustomerRepository {

    public CustomerRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Customer> getEntityClass() {
        return Customer.class;
    }

    @Override
    public String getUniqueFieldName() {
        return Profile_.EMAIL;
    }
}
