package application.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@StaticMetamodel(Card.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Card_ extends application.entity.BaseEntity_ {

	public static final String LOCAL_DATE_TIME = "localDateTime";
	public static final String CCV2 = "ccv2";
	public static final String SECOND_PASSWORD = "secondPassword";
	public static final String AMOUNT_CARD = "amountCard";
	public static final String BANK_NAME = "bankName";
	public static final String CARD_NUMBER = "cardNumber";

	
	/**
	 * @see application.entity.Card#localDateTime
	 **/
	public static volatile SingularAttribute<Card, LocalDateTime> localDateTime;
	
	/**
	 * @see application.entity.Card#ccv2
	 **/
	public static volatile SingularAttribute<Card, String> ccv2;
	
	/**
	 * @see application.entity.Card#secondPassword
	 **/
	public static volatile SingularAttribute<Card, String> secondPassword;
	
	/**
	 * @see application.entity.Card#amountCard
	 **/
	public static volatile SingularAttribute<Card, Integer> amountCard;
	
	/**
	 * @see application.entity.Card#bankName
	 **/
	public static volatile SingularAttribute<Card, String> bankName;
	
	/**
	 * @see application.entity.Card
	 **/
	public static volatile EntityType<Card> class_;
	
	/**
	 * @see application.entity.Card#cardNumber
	 **/
	public static volatile SingularAttribute<Card, String> cardNumber;

}

