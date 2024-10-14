package application.entity.users.userFactory;

import application.dto.UserSignupRequestDto;
import application.entity.Credit;
import application.entity.users.Customer;
import application.entity.users.Profile;
import application.entity.users.Users;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CustomerFactory implements UserFactory {
    @Override
    public Users createUser(UserSignupRequestDto userSignupRequestDto) {
        Customer customer = Customer.builder()
                .firstName(userSignupRequestDto.getFirstName())
                .lastName(userSignupRequestDto.getLastName())
                .profile(
                        Profile.builder()
                                .email(userSignupRequestDto.getEmail())
                                .password(userSignupRequestDto.getPassword()).build())
                .dateTimeSubmission(LocalDateTime.now())
                .credit(Credit.builder().amount(0).build())
                .build();
        customer.setImage(userSignupRequestDto.getInputStream());
        return customer;
    }
}
