package application.service.impl;

import application.dto.UserSignupRequest;
import application.entity.users.Customer;
import application.entity.users.Expert;
import application.entity.users.Users;
import application.entity.users.userFactory.CustomerFactory;
import application.entity.users.userFactory.ExpertFactory;
import application.service.CustomerService;
import application.service.ExpertService;
import application.service.PasswordEncode;
import application.service.SignupService;
import application.util.AuthHolder;

public class SignupServiceImpl implements SignupService {

    private final ExpertService expertService;
    private final CustomerService customerService;
    private final PasswordEncode passwordEncode;
    private final AuthHolder authHolder;

    public SignupServiceImpl(ExpertService expertService, CustomerService customerService, PasswordEncode passwordEncode, AuthHolder authHolder) {
        this.expertService = expertService;
        this.customerService = customerService;
        this.passwordEncode = passwordEncode;
        this.authHolder = authHolder;
    }

    @Override
    public Users signup(UserSignupRequest userSignupRequest) {

        switch (userSignupRequest.getRole()){
            case "Expert": {
                //todo email unique check first in if
                //todo expertFactory from application context
                ExpertFactory expertFactory = new ExpertFactory();
                userSignupRequest.setPassword(
                        passwordEncode.encode(userSignupRequest.getPassword())
                );
                Expert expert = (Expert) expertFactory.createUser(userSignupRequest);
                expert = expertService.save(expert);
                authHolder.tokenName=expert.getProfile().getEmail();
                authHolder.tokenId=expert.getId();
                //todo?? adminService.updateExpertStatus(expert); for request to admin to set expert status
                //todo notification
                return expert;
            }
            case "Customer":{
                CustomerFactory customerFactory =new CustomerFactory();
                userSignupRequest.setPassword(
                        passwordEncode.encode(userSignupRequest.getPassword())
                );
                Customer customer = (Customer) customerFactory.createUser(userSignupRequest);
                customer = customerService.save(customer);
                authHolder.tokenId=customer.getId();
                authHolder.tokenName=customer.getProfile().getEmail();
                return customer;
            }
            default:throw new IllegalArgumentException("Only Expert or Customer can sign up");
        }


    }
}
