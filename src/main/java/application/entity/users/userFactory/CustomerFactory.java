package application.entity.users.userFactory;

import application.dto.UserSignupRequest;
import application.entity.users.Customer;
import application.entity.users.Profile;
import application.entity.users.Users;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
@Component
public class CustomerFactory implements UserFactory {
    @Override
    public Users createUser(UserSignupRequest userSignupRequest) {
        Customer customer = Customer.builder()
                .firstName(userSignupRequest.getFirstName())
                .lastName(userSignupRequest.getLastName())
                .profile(
                        Profile.builder()
                                .email(userSignupRequest.getEmail())
                                .password(userSignupRequest.getPassword()).build())
                .dateTimeSubmission(LocalDateTime.now())
                .build();
        customer.setImage(userSignupRequest.getInputStream());
        return customer;
    }
}
