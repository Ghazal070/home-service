package application.service.impl;

import application.dto.UserSignupRequest;
import application.entity.users.Customer;
import application.entity.users.Expert;
import application.entity.users.Users;
import application.entity.users.userFactory.CustomerFactory;
import application.entity.users.userFactory.ExpertFactory;
import application.service.CustomerService;
import application.service.ExpertService;
import application.service.PasswordEncodeService;
import application.service.SignupService;
import application.util.AuthHolder;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

@Service
public class SignupServiceImpl implements SignupService {

    private final ExpertService expertService;
    private final CustomerService customerService;
    private final PasswordEncodeService passwordEncodeService;
    private final AuthHolder authHolder;
    private final ExpertFactory expertFactory;
    private final CustomerFactory customerFactory;

    public SignupServiceImpl(ExpertService expertService, CustomerService customerService, PasswordEncodeService passwordEncodeService, AuthHolder authHolder, ExpertFactory expertFactory, CustomerFactory customerFactory) {
        this.expertService = expertService;
        this.customerService = customerService;
        this.passwordEncodeService = passwordEncodeService;
        this.authHolder = authHolder;
        this.expertFactory = expertFactory;
        this.customerFactory = customerFactory;
    }

    @Override
    public Users signup(UserSignupRequest userSignupRequest) {

        switch (userSignupRequest.getRole()){
            case "Expert": {
                //done email unique check first in if
                //done expertFactory from application context
                if(expertService.containByUniqField(userSignupRequest.getEmail())){
                    throw new ValidationException("Email must be unique");
                }
                userSignupRequest.setPassword(
                        passwordEncodeService.encode(userSignupRequest.getPassword())
                );
                Expert expert = (Expert) expertFactory.createUser(userSignupRequest);
                expert = expertService.save(expert);
                authHolder.tokenName=expert.getProfile().getEmail();
                authHolder.tokenId=expert.getId();
                return expert;
            }
            case "Customer":{
                if(customerService.containByUniqField(userSignupRequest.getEmail())){
                    throw new ValidationException("Email must be unique");
                }
                userSignupRequest.setPassword(
                        passwordEncodeService.encode(userSignupRequest.getPassword())
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
