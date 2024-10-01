package application.service.impl;

import application.dto.projection.UserLoginProjection;
import application.entity.users.Customer;
import application.entity.users.Profile;
import application.repository.CustomerRepository;
import application.service.DutyService;
import application.service.OrderService;
import application.service.PasswordEncode;
import application.util.AuthHolder;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private DutyService dutyService;

    @Mock
    private OrderService orderService;

    @Mock
    private CustomerRepository repository;

    private Validator validator;

    @Mock
    private AuthHolder authHolder;

    @Mock
    private PasswordEncode passwordEncode;

    @InjectMocks
    private CustomerServiceImpl underTest;
    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        underTest = new CustomerServiceImpl(validator, repository, authHolder,
                passwordEncode, dutyService, orderService);
        authHolder=new AuthHolder(100,"gh@dg.com");
    }

    @Test
    void orderSubmit() {
    }

    @Test
    void isCustomerAuthenticatedSuccessfully() {
        Optional<Customer> customer = Optional.ofNullable(Customer.builder().id(100).profile(
                Profile.builder().email("gh@dg.com").password("ghazal99").build()
        ).build());

        given(authHolder.getTokenId()).willReturn(customer.get().getId());
        given(repository.findById(100)).willReturn(customer);


        boolean actual = underTest.isCustomerAuthenticated();

        assertTrue(actual);
    }
}