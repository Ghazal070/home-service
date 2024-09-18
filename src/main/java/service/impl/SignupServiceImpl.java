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
import util.AuthHolder;

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
                ExpertFactory expertFactory = new ExpertFactory();
                userSignupRequest.setPassword(
                        passwordEncode.encode(userSignupRequest.getPassword())
                );
                Expert expert = (Expert) expertFactory.createUser(userSignupRequest);
                expert = expertService.save(expert);
                authHolder.tokenName=expert.getProfile().getEmail();
                authHolder.tokenId=expert.getId();
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
