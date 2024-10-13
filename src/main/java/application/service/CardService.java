package application.service;

import application.dto.CardDto;
import application.entity.Card;
import application.entity.Order;

import java.util.Set;


public interface CardService extends BaseEntityService<Card,Integer>{

    Card validateCard(CardDto cardDto);

}
