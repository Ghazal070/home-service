package service.impl;

import dto.UserSignupRequest;
import entity.users.Customer;
import entity.users.Expert;
import entity.users.Users;
import entity.users.userFactory.CustomerFactory;
import entity.users.userFactory.ExpertFactory;
import service.CustomerService;
import service.ExpertService;
import service.PasswordEncode;
import service.SignupService;

public class SignupServiceImpl implements SignupService {

    private final ExpertService expertService;
    private final CustomerService customerService;
    private final PasswordEncode passwordEncode;

    public SignupServiceImpl(ExpertService expertService, CustomerService customerService, PasswordEncode passwordEncode) {
        this.expertService = expertService;
        this.customerService = customerService;
        this.passwordEncode = passwordEncode;
    }

    @Override
    public Users signup(UserSignupRequest userSignupRequest) {

        switch (userSignupRequest.getRole()){
            case "Expert": {
                ExpertFactory expertFactory = new ExpertFactory();
                userSignupRequest.setPassword(
                        passwordEncode.encode(userSignupRequest.getPassword())
                );
                Expert expert = (Expert) expertFactory.createUser(userSignupRequest);
                return expertService.save(expert);
            }
            case "Customer":{
                CustomerFactory customerFactory =new CustomerFactory();
                userSignupRequest.setPassword(
                        passwordEncode.encode(userSignupRequest.getPassword())
                );
                Customer customer = (Customer) customerFactory.createUser(userSignupRequest);
                return customerService.save(customer);
            }
            default:throw new IllegalArgumentException("Only Expert or Customer can sign up");
        }


    }
}
