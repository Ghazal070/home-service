package service.impl;

import entity.users.Customer;
import repository.CustomerRepository;


public class CustomerServiceImpl extends UserServiceImpl<CustomerRepository, Customer>{
    public CustomerServiceImpl(CustomerRepository repository) {
        super(repository);
    }
}
