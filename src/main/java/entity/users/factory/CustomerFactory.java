package entity.users.factory;

import dto.UserSignupRequest;
import entity.users.Customer;
import entity.users.Profile;
import entity.users.User;

import java.time.ZonedDateTime;

public class CustomerFactory implements UserFactory {
    @Override
    public User createUser(UserSignupRequest userSignupRequest) {
        Customer customer = Customer.builder()
                .firstName(userSignupRequest.getFirstName())
                .lastName(userSignupRequest.getLastName())
                .profile(
                        Profile.builder()
                                .email(userSignupRequest.getEmail())
                                .password(userSignupRequest.getPassword()).build())
                .dateTimeSubmission(ZonedDateTime.now())
                .build();
        customer.getBytes(userSignupRequest.getPathImage());
        return customer;
    }
}
