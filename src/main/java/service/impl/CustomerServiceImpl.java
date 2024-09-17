package service.impl;

import entity.users.Customer;
import repository.CustomerRepository;
import service.CustomerService;


public class CustomerServiceImpl extends UserServiceImpl<CustomerRepository, Customer> implements CustomerService {
    public CustomerServiceImpl(CustomerRepository repository) {
        super(repository);
    }
}
