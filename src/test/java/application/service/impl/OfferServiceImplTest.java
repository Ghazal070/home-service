package application.service.impl;

import application.entity.Offer;
import application.repository.ExpertRepository;
import application.repository.OfferRepository;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class OfferServiceImplTest {

    @InjectMocks
    private OfferServiceImpl underTest;

    @Mock
    private OfferRepository repository;


    private Validator validator;
    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.underTest = new OfferServiceImpl(validator, repository);
    }


    @Test
    void getOfferByCustomerIdOrderByScoreExpert() {

        Integer customerId = 1;
        Integer orderId = 1;
        Offer offer1 = new Offer();
        Offer offer2 = new Offer();
        Set<Offer> expectedOffers = Set.of(offer1, offer2);

        given(repository.getOfferByCustomerIdOrderByScoreExpert(customerId, orderId)).willReturn(expectedOffers);

        Set<Offer> actualOffers = underTest.getOfferByCustomerIdOrderByScoreExpert(customerId, orderId);

        assertNotNull(actualOffers);
        assertEquals(2, actualOffers.size());
        assertTrue(actualOffers.containsAll(expectedOffers));
    }

    @Test
    void getOfferByCustomerIdOrderByPriceOrder() {

        Integer customerId = 1;
        Integer orderId = 1;
        Offer offer1 = new Offer();
        Offer offer2 = new Offer();
        Set<Offer> expectedOffers = Set.of(offer1, offer2);

        given(repository.getOfferByCustomerIdOrderByPriceOrder(customerId, orderId)).willReturn(expectedOffers);

        Set<Offer> actualOffers = underTest.getOfferByCustomerIdOrderByPriceOrder(customerId, orderId);

        assertNotNull(actualOffers);
        assertEquals(2, actualOffers.size());
        assertTrue(actualOffers.containsAll(expectedOffers));
    }
}