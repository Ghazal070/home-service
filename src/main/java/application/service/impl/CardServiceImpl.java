package application.service.impl;

import application.dto.CardDto;
import application.entity.Card;
import application.repository.CardRepository;
import application.service.CardService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class CardServiceImpl extends BaseEntityServiceImpl<CardRepository, Card, Integer> implements CardService {


    public CardServiceImpl(Validator validator, CardRepository repository) {
        super(validator, repository);
    }

    @PostConstruct
     public void init() {
        if (repository.count() == 0) {
            Card card_1 = Card.builder()
                    .cardNumber("1111-2222")
                    .bankName("Melli")
                    .localDateTime(LocalDateTime.now().plus(3, ChronoUnit.YEARS))
                    .secondPassword("1111")
                    .ccv2("1111")
                    .amountCard(200_000_000)
                    .build();

            Card card_2 = Card.builder()
                    .cardNumber("2222-3333")
                    .bankName("Melli")
                    .localDateTime(LocalDateTime.now().plus(3, ChronoUnit.YEARS))
                    .secondPassword("2222")
                    .ccv2("222200000000000")
                    .amountCard(200_000_000)
                    .build();
            repository.save(card_1);
            repository.save(card_2);
        }
    }

    @Override
    public Card validateCard(CardDto cardDto) {
        Card existCard = repository.findByCardNumber(cardDto.getCardNumber());
        if (!cardDto.equals(existCard))
            throw new ValidationException("your card is not ok");
        return existCard;
    }
}
