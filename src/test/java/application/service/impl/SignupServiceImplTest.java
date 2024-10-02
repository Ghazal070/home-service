package application.service.impl;

import application.dto.UserSignupRequest;
import application.entity.users.Customer;
import application.entity.users.Expert;
import application.entity.users.Profile;
import application.entity.users.Users;
import application.entity.users.userFactory.CustomerFactory;
import application.entity.users.userFactory.ExpertFactory;
import application.service.CustomerService;
import application.service.ExpertService;
import application.service.PasswordEncode;
import application.util.AuthHolder;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class SignupServiceImplTest {

    @Mock
    private ExpertService expertService;

    @Mock
    private CustomerService customerService;

    @Mock
    private PasswordEncode passwordEncode;

    @Mock
    private AuthHolder authHolder;

    @Mock
    private ExpertFactory expertFactory;

    @Mock
    private CustomerFactory customerFactory;

    @Autowired
    @InjectMocks
    private SignupServiceImpl underTest;

    @BeforeEach
    void setUp() {
        this.underTest =new SignupServiceImpl(expertService,customerService,passwordEncode,authHolder,expertFactory,customerFactory);
    }

    @Test
    void signupCustomerSuccessfully() {
        UserSignupRequest userSignupRequest = null;
        try {
            userSignupRequest = UserSignupRequest.builder().email("customer@example.com")
                    .password("ghazal99")
                    .role("Customer")
                    .password("pass1234")
                    .inputStream(new FileInputStream("src/main/resources/images/less300.jpg"))
                    .build();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Customer customer = Customer.builder().id(100).profile(Profile.builder()
                .email(userSignupRequest.getEmail())
                .password(userSignupRequest.getPassword())
                .build()).build();
        given(customerService.containByUniqField(userSignupRequest.getEmail())).willReturn(false);
       given(passwordEncode.encode(userSignupRequest.getPassword())).willReturn("pass1234");
        given(customerFactory.createUser(any(UserSignupRequest.class))).willReturn(customer);
        given(customerService.save(any(Customer.class))).willReturn(customer);

        Users actual = underTest.signup(userSignupRequest);

        assertNotNull(actual);
        verify(customerService).containByUniqField(userSignupRequest.getEmail());
        verify(passwordEncode).encode(userSignupRequest.getPassword());
        verify(customerFactory).createUser(userSignupRequest);
        verify(customerService).save(any(Customer.class));
        assertEquals(userSignupRequest.getEmail(), actual.getProfile().getEmail());

    }

    @Test
    void signupExpertSuccessfully() {
        UserSignupRequest userSignupRequest = null;
        try {
            userSignupRequest = UserSignupRequest.builder().email("expert@example.com")
                    .password("ghazal99")
                    .role("Expert")
                    .password("pass1234")
                    .inputStream(new FileInputStream("src/main/resources/images/less300.jpg"))
                    .build();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Expert expert = Expert.builder().id(100).profile(Profile.builder()
                .email(userSignupRequest.getEmail())
                .password(userSignupRequest.getPassword())
                .build()).build();
        given(expertService.containByUniqField(userSignupRequest.getEmail())).willReturn(false);
        given(passwordEncode.encode(userSignupRequest.getPassword())).willReturn("pass1234");
        given(expertFactory.createUser(any(UserSignupRequest.class))).willReturn(expert);
        given(expertService.save(any(Expert.class))).willReturn(expert);

        Users actual = underTest.signup(userSignupRequest);

        assertNotNull(actual);
        verify(expertService).containByUniqField(userSignupRequest.getEmail());
        verify(passwordEncode).encode(userSignupRequest.getPassword());
        verify(expertFactory).createUser(userSignupRequest);
        verify(expertService).save(any(Expert.class));
        assertEquals(userSignupRequest.getEmail(), actual.getProfile().getEmail());

    }

    @Test
    void signupInvalidRoleRole() {
        UserSignupRequest userSignupRequest;
        try {
            userSignupRequest = UserSignupRequest.builder().email("expert@example.com")
                    .password("ghazal99")
                    .role("InvalidRole")
                    .password("pass1234")
                    .inputStream(new FileInputStream("src/main/resources/images/less300.jpg"))
                    .build();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertThatThrownBy(()->underTest.signup(userSignupRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Only Expert or Customer can sign up");

    }

    @Test
    void signupDuplicateEmail() {
        UserSignupRequest userSignupRequest;
        try {
            userSignupRequest = UserSignupRequest.builder().email("expert@example.com")
                    .password("ghazal99")
                    .role("Customer")
                    .password("pass1234")
                    .inputStream(new FileInputStream("src/main/resources/images/less300.jpg"))
                    .build();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        given(customerService.containByUniqField(userSignupRequest.getEmail())).willReturn(true);

        assertThatThrownBy(()->underTest.signup(userSignupRequest))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Email must be unique");
        verify(customerService).containByUniqField(userSignupRequest.getEmail());


    }
}