package application.entity.users.userFactory;

import application.constants.RoleNames;
import application.dto.UserSignupRequestDto;
import application.entity.Credit;
import application.entity.users.Customer;
import application.entity.users.Profile;
import application.entity.users.Users;
import application.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CustomerFactory implements UserFactory {
    private final RoleService roleService;
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
                .credit(Credit.builder().amount(0.0).build())
                .roles(Set.of(roleService.findByName(RoleNames.CUSTOMER).get()))
                .build();
        customer.setImage(userSignupRequestDto.getInputStream());
        return customer;
    }
}
