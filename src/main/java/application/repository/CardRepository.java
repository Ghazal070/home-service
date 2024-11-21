package application.repository;

import application.entity.Card;
import application.entity.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CardRepository extends BaseEntityRepository<Card, Integer> {


    Card findByCardNumber(String cardNumber);
}
