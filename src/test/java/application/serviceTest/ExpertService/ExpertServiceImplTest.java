package application.serviceTest.ExpertService;

import application.dto.OfferCreationDto;
import application.entity.Duty;
import application.entity.Offer;
import application.entity.Order;
import application.entity.enumeration.ExpertStatus;
import application.entity.enumeration.OrderStatus;
import application.entity.users.Expert;
import jakarta.validation.ValidationException;
import application.repository.ExpertRepository;
import application.service.OfferService;
import application.service.OrderService;
import application.service.PasswordEncodeService;
import application.service.impl.ExpertServiceImpl;
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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ExpertServiceImplTest {

    @InjectMocks
    private ExpertServiceImpl underTest;
    @Mock
    private OrderService orderService;

    @Mock
    private OfferService offerService;

    @Mock
    private ExpertRepository repository;
    @Mock
    private PasswordEncodeService passwordEncodeService;

    @Mock
    private AuthHolder authHolder;
    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.underTest = new ExpertServiceImpl(validator, repository, authHolder
                , passwordEncodeService, orderService, offerService);
    }


    @Test
    public void testHavePermissionTrueExpertToServices() {
        Expert expert = Expert.builder().id(100).expertStatus(ExpertStatus.Accepted).build();
        given(repository.findById(100)).willReturn(Optional.of(expert));

        Boolean actual = underTest.havePermissionExpertToServices(expert.getId());

        assertTrue(actual);
    }

    @Test
    public void testHavePermissionFalseExpertToServices() {
        Expert expert = Expert.builder().id(100).expertStatus(ExpertStatus.New).build();

        Boolean actual = underTest.havePermissionExpertToServices(expert.getId());

        assertFalse(actual);

    }


    @Test
    public void testSendOfferSuccess() {
        OfferCreationDto offerCreationDto = OfferCreationDto.builder()
                .orderId(100).priceOffer(2_000)
                .lengthDays(5)
                .dateTimeStartWork(LocalDateTime.now().plus(5, ChronoUnit.DAYS))
                .build();
        Duty duty = Duty.builder().id(50).basePrice(1_000).build();
        Set<Duty> duties = Set.of(duty);
        Order order = Order.builder().id(100).priceOrder(2_500).duty(duty).build();
        Expert expert = Expert.builder().id(1).duties(duties).build();
        given(orderService.findById(offerCreationDto.getOrderId())).willReturn(Optional.of(order));
        given(repository.findById(1)).willReturn(Optional.of(expert));
        given(orderService.getOrdersForExpertWaitingOrChoosing(1)).willReturn(Set.of(order));
        given(offerService.save(any(Offer.class))).willReturn(new Offer());

        Offer savedOffer = underTest.sendOffer(offerCreationDto,expert.getId());

        assertNotNull(savedOffer);
        assertEquals(OrderStatus.ExpertChooseWanting, order.getOrderStatus());
        verify(orderService).update(order);
    }
    @Test
    public void testSendOfferOrderNotFound() {
        OfferCreationDto offerCreationDto = OfferCreationDto.builder()
                .orderId(100).priceOffer(2_000)
                .lengthDays(5)
                .dateTimeStartWork(LocalDateTime.now().plus(5, ChronoUnit.DAYS))
                .build();
        Expert expert = Expert.builder().id(1).build();
        given(orderService.findById(offerCreationDto.getOrderId())).willReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.sendOffer(offerCreationDto,expert.getId()))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Order is null");

    }

    @Test
    public void testSendOfferOrderNotInList() {
        OfferCreationDto offerCreationDto = OfferCreationDto.builder()
                .orderId(100).priceOffer(2_000)
                .lengthDays(5)
                .dateTimeStartWork(LocalDateTime.now().plus(5, ChronoUnit.DAYS))
                .build();
        Duty duty = Duty.builder().id(50).basePrice(1_000).build();
        Expert expert = Expert.builder().id(1).build();
        Order order = Order.builder().id(100).priceOrder(2_500).duty(duty).build();
        given(orderService.findById(offerCreationDto.getOrderId())).willReturn(Optional.of(order));
        given(orderService.getOrdersForExpertWaitingOrChoosing(1)).willReturn(new HashSet<>());

        assertThatThrownBy(() -> underTest.sendOffer(offerCreationDto,expert.getId()))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("This order is not in list expert order or not waiting");
    }

    @Test
    public void testSendOfferBasePriceGreaterThanOffer() {
        OfferCreationDto offerCreationDto = OfferCreationDto.builder()
                .orderId(100).priceOffer(2_000)
                .lengthDays(5)
                .dateTimeStartWork(LocalDateTime.now().plus(5, ChronoUnit.DAYS))
                .build();
        Expert expert = Expert.builder().id(1).build();
        Duty duty = Duty.builder().id(50).basePrice(8_000).build();
        Order order = Order.builder().id(100).priceOrder(2_500).duty(duty).build();
        given(orderService.findById(offerCreationDto.getOrderId())).willReturn(Optional.of(order));
        given(orderService.getOrdersForExpertWaitingOrChoosing(1)).willReturn(Set.of(order));

        assertThatThrownBy(() -> underTest.sendOffer(offerCreationDto,expert.getId()))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Base price greater than your offer");
    }

    @Test
    public void testSendOfferOfferNotSaved() {
        OfferCreationDto offerCreationDto = OfferCreationDto.builder()
                .orderId(100).priceOffer(2_000)
                .lengthDays(5)
                .dateTimeStartWork(LocalDateTime.now().plus(5, ChronoUnit.DAYS))
                .build();
        Duty duty = Duty.builder().id(50).basePrice(1_000).build();
        Set<Duty> duties = Set.of(duty);
        Order order = Order.builder().id(100).priceOrder(2_500).duty(duty).build();
        Expert expert = Expert.builder().id(1).duties(duties).build();
        given(orderService.findById(offerCreationDto.getOrderId())).willReturn(Optional.of(order));
        given(repository.findById(1)).willReturn(Optional.of(expert));
        given(orderService.getOrdersForExpertWaitingOrChoosing(1)).willReturn(Set.of(order));
        given(offerService.save(any(Offer.class))).willReturn(null);

        assertThatThrownBy(() -> underTest.sendOffer(offerCreationDto,expert.getId()))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Error in saving offer");
    }
}