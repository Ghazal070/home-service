package service.impl;

import entity.users.Customer;
import repository.CustomerRepository;
import service.CustomerService;
import service.PasswordEncode;
import util.AuthHolder;


public class CustomerServiceImpl extends UserServiceImpl<CustomerRepository, Customer> implements CustomerService {
    public CustomerServiceImpl(CustomerRepository repository, AuthHolder authHolder, PasswordEncode passwordEncode) {
        super(repository, authHolder, passwordEncode);
    }
}
