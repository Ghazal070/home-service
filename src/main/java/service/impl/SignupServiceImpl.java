package service.impl;

import dto.UserSignupRequest;
import entity.BaseEntity;
import entity.enumeration.Role;
import entity.users.Customer;
import entity.users.Profile;
import entity.users.User;
import service.CustomerService;
import service.SignupService;
import service.UserService;

import java.time.ZonedDateTime;

public class SignupServiceImpl implements SignupService {

    private final UserService userService;

    public SignupServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User signup(UserSignupRequest userSignupRequest) {
        if (userSignupRequest.getRole()== Role.Customer){
            userService.save(
                    Customer.builder().firstName(userSignupRequest.getFirstName())
                            .lastName(userSignupRequest.getLastName())
                            .profile(
                                    Profile.builder().email(userSignupRequest.getEmail())
                                            .password(userSignupRequest.getPassword()).build())
                            .dateTimeSubmission(ZonedDateTime.now())
                            .image(userSignupRequest.getImage())
                            .build()
            )
        }
        return null;
    }
}
