package application.service.impl;

import application.entity.users.Customer;
import application.repository.CustomerRepository;
import application.service.CustomerService;
import application.service.PasswordEncode;
import application.util.AuthHolder;


public class CustomerServiceImpl extends UserServiceImpl<CustomerRepository, Customer> implements CustomerService {
    public CustomerServiceImpl(CustomerRepository repository, AuthHolder authHolder, PasswordEncode passwordEncode) {
        super(repository, authHolder, passwordEncode);
    }
}
