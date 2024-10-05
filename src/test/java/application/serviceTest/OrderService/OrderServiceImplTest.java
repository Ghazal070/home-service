package application.serviceTest.OrderService;

import application.entity.Order;
import application.repository.OrderRepository;
import application.service.impl.OrderServiceImpl;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    private Validator validator;

    @Mock
    private OrderRepository repository;

    @InjectMocks
    private OrderServiceImpl underTest;

    @BeforeEach
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
       underTest = new OrderServiceImpl(validator,repository);
    }

    @Test
    public void testGetOrdersForExpertSuccessfully() {
        Integer expertId = 1;
        Set<Order> expectedOrders = new HashSet<>();
        expectedOrders.add(new Order());


        given(repository.getOrdersForExpert(expertId)).willReturn(expectedOrders);

        Set<Order> actualOrders = underTest.getOrdersForExpert(expertId);

        assertEquals(expectedOrders, actualOrders);
        verify(repository).getOrdersForExpert(expertId);
    }

    @Test
    public void testGetOrdersForExpertNoOrders() {
        Integer expertId = 1;
        Set<Order> expectedOrders = new HashSet<>();

        given(repository.getOrdersForExpert(expertId)).willReturn(expectedOrders);

        Set<Order> actualOrders = underTest.getOrdersForExpert(expertId);

        assertEquals(expectedOrders, actualOrders);
        verify(repository).getOrdersForExpert(expertId);
    }
}