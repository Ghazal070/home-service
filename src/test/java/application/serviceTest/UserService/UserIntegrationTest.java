package application.serviceTest.UserService;

import application.dto.OrderSubmission;
import application.dto.UserChangePassword;
import application.dto.UserSignupRequest;
import application.dto.projection.UserLoginProjection;
import application.entity.Duty;
import application.entity.Order;
import application.entity.users.Users;
import application.repository.UserRepository;
import application.service.CustomerService;
import application.service.DutyService;
import application.service.SignupService;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserIntegrationTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SignupService signupService;
    @Autowired
    private DutyService dutyService;

    @Autowired
    private UserRepository userRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    @Test
    public void testSignupLoginUpdatePassword() throws Exception {
        String originalPassword = "pass1234";
        UserSignupRequest userSignupRequest = createSignupRequest("Customer", originalPassword);

        Users newUser = signupService.signup(userSignupRequest);
        assertThat(newUser).isNotNull();
        assertThat(newUser.getProfile().getEmail()).isEqualTo(userSignupRequest.getEmail());

        UserLoginProjection loginUser = customerService.login(userSignupRequest.getEmail(), originalPassword);
        assertThat(loginUser).isNotNull();
        assertThat(loginUser.getProfile().getEmail()).isEqualTo(userSignupRequest.getEmail());

        String newPassword = "PASS5678";

        UserChangePassword userChangePassword = UserChangePassword.builder()
                .oldPassword(originalPassword)
                .newPassword(newPassword)
                .build();

        Boolean isUpdated = customerService.updatePassword(userChangePassword);
        assertThat(isUpdated).isTrue();

        UserLoginProjection updatedLoginUser = customerService.login(userSignupRequest.getEmail(), newPassword);
        assertThat(updatedLoginUser).isNotNull();
        assertThat(updatedLoginUser.getProfile().getEmail()).isEqualTo(userSignupRequest.getEmail());

        List<Duty> selectableDuties = dutyService.getSelectableDuties();

        assertThat(selectableDuties).allMatch(a->a.getSelectable().equals(true));


        OrderSubmission orderSubmission = OrderSubmission.builder()
                .dutyId(299)
                .priceOrder(900_000)
                .dateTimeOrder(LocalDateTime.of(2024, 10, 30, 10, 25))
                .address(faker.address().streetAddress())
                .description("8 --- " + faker.lorem().characters(5, 20))
                .build();
        Order order = customerService.orderSubmit(orderSubmission);
         assertNotNull(order);
         assertThat(order.getDuty().getId()).isEqualTo(orderSubmission.getDutyId());

    }

    private UserSignupRequest createSignupRequest(String role, String password) throws FileNotFoundException {
        return UserSignupRequest.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .password(password)
                .inputStream(new FileInputStream("src/main/resources/images/less300.jpg"))
                .role(role)
                .build();
    }
}