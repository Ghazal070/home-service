package application.service.impl;

import application.dto.OrderSubmission;
import application.entity.Duty;
import application.entity.Order;
import application.entity.enumeration.OrderStatus;
import application.entity.users.Customer;
import application.repository.CustomerRepository;
import application.service.DutyService;
import application.service.OrderService;
import application.service.PasswordEncode;
import application.util.AuthHolder;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

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
    }

    @Test
    void orderSubmitSuccessfully() {
        Customer customer = Customer.builder().id(100).build();
        Duty duty = Duty.builder().id(120).basePrice(1_000).title("Wash Dishes").selectable(true).build();
        Duty spyDuty = spy(duty);
        OrderSubmission orderSubmission = OrderSubmission.builder().priceOrder(2_000).dutyId(120).build();
        OrderSubmission spyOrderSubmission = spy(orderSubmission);
        doReturn(10_000).when(spyOrderSubmission).getPriceOrder();
        given(authHolder.getTokenId()).willReturn(customer.getId());
        given(repository.findById(customer.getId())).willReturn(Optional.of(customer));
        given(dutyService.findById(spyDuty.getId())).willReturn(Optional.of(spyDuty));
        doReturn(true).when(spyDuty).getSelectable();
        given(orderService.save(any(Order.class))).willReturn(new Order());

        Order order = underTest.orderSubmit(spyOrderSubmission);

        assertNotNull(order);
        assertEquals(OrderStatus.ExpertOfferWanting,order.getOrderStatus());
    }
    @Test
    void orderSubmitIsNull() {
        Customer customer = Customer.builder().id(100).build();
        OrderSubmission orderSubmission = OrderSubmission.builder().priceOrder(2_000).dutyId(120).build();
        given(authHolder.getTokenId()).willReturn(customer.getId());
        given(repository.findById(customer.getId())).willReturn(Optional.of(customer));

        assertThatThrownBy(()->underTest.orderSubmit(orderSubmission))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("OrderSubmission is null");
    }
    @Test
    void orderSubmitNotAuthenticated() {
        Customer customer = Customer.builder().id(100).build();
        OrderSubmission orderSubmission = OrderSubmission.builder().priceOrder(2_000).dutyId(120).build();
        given(authHolder.getTokenId()).willReturn(customer.getId());
        given(repository.findById(customer.getId())).willReturn(Optional.empty());

        assertThatThrownBy(()->underTest.orderSubmit(orderSubmission))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Customer must be logged in to view duties.");
    }
    @Test
    void orderSubmitNotFoundDuty() {
        Customer customer = Customer.builder().id(100).build();
        Duty duty = Duty.builder().id(120).basePrice(1_000).title("Wash Dishes").selectable(true).build();
        OrderSubmission orderSubmission = OrderSubmission.builder().priceOrder(2_000).dutyId(120).build();
        given(authHolder.getTokenId()).willReturn(customer.getId());
        given(repository.findById(customer.getId())).willReturn(Optional.of(customer));
        given(dutyService.findById(duty.getId())).willReturn(Optional.empty());

        assertThatThrownBy(()->underTest.orderSubmit(orderSubmission))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Title duty must be from Duty and be selectable List or auth holder is null");

    }

    @Test
    void orderSubmitNotSelectable() {
        Customer customer = Customer.builder().id(100).build();
        Duty duty = spy(
                Duty.builder().id(120)
                        .selectable(false)
                        .build()
        );
        OrderSubmission orderSubmission = OrderSubmission.builder().priceOrder(2_000).dutyId(120).build();
        given(authHolder.getTokenId()).willReturn(customer.getId());
        given(repository.findById(customer.getId())).willReturn(Optional.of(customer));
        given(dutyService.findById(duty.getId())).willReturn(Optional.of(duty));
        doReturn(false).when(duty).getSelectable();

        assertThatThrownBy(()->underTest.orderSubmit(orderSubmission))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Title duty must be from Duty and be selectable List or auth holder is null");
    }
    @Test
    void orderSubmitPriceLowerTHanBasePrice() {
        Customer customer = Customer.builder().id(100).build();
        Duty duty = Duty.builder().id(120).basePrice(1_000).title("Wash Dishes").selectable(true).build();
        Duty spyDuty = spy(duty);
        OrderSubmission orderSubmission = OrderSubmission.builder().priceOrder(2_000).dutyId(120).build();
        OrderSubmission spyOrderSubmission = spy(orderSubmission);
        doReturn(1_00).when(spyOrderSubmission).getPriceOrder();
        given(authHolder.getTokenId()).willReturn(customer.getId());
        given(repository.findById(customer.getId())).willReturn(Optional.of(customer));
        given(dutyService.findById(spyDuty.getId())).willReturn(Optional.of(spyDuty));
        doReturn(true).when(spyDuty).getSelectable();

        assertThatThrownBy(()->underTest.orderSubmit(spyOrderSubmission))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Duty base price greater than your order");
    }

    @Test
    void isCustomerAuthenticatedSuccessfully() {
        Optional<Customer> customer = Optional.ofNullable(Customer.builder().id(100).build());
        given(authHolder.getTokenId()).willReturn(customer.get().getId());
        given(repository.findById(100)).willReturn(customer);

        Optional<Customer> actual = underTest.isCustomerAuthenticated();

        assertNotNull(actual);
    }

    @Test
    void isCustomerAuthenticatedFailedCustomerNotExist() {
        Optional<Customer> customer = Optional.ofNullable(Customer.builder().id(100).build());
        given(authHolder.getTokenId()).willReturn(customer.get().getId());
        given(repository.findById(100)).willReturn(Optional.empty());

        Optional<Customer> actual = underTest.isCustomerAuthenticated();

        assertThat(actual).isEmpty();
    }

    @Test
    void isCustomerAuthenticatedFailedAuthHolderIsNull() {
        Optional<Customer> customer = Optional.ofNullable(Customer.builder().id(100).build());

        Optional<Customer> actual = underTest.isCustomerAuthenticated();

        assertThat(actual).isEmpty();
    }
}