package application.entity;

import application.entity.users.Expert;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.ZonedDateTime;

@StaticMetamodel(Offer.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Offer_ extends application.entity.BaseEntity_ {

	public static final String EXPERT = "expert";
	public static final String PRICE_OFFER = "priceOffer";
	public static final String DATE_TIME_START_WORK = "dateTimeStartWork";
	public static final String DATE_TIME_OFFER = "dateTimeOffer";
	public static final String LENGTH_DAYS = "lengthDays";
	public static final String ORDER = "order";

	
	/**
	 * @see application.entity.Offer#expert
	 **/
	public static volatile SingularAttribute<Offer, Expert> expert;
	
	/**
	 * @see application.entity.Offer#priceOffer
	 **/
	public static volatile SingularAttribute<Offer, Integer> priceOffer;
	
	/**
	 * @see application.entity.Offer#dateTimeStartWork
	 **/
	public static volatile SingularAttribute<Offer, ZonedDateTime> dateTimeStartWork;
	
	/**
	 * @see application.entity.Offer#dateTimeOffer
	 **/
	public static volatile SingularAttribute<Offer, ZonedDateTime> dateTimeOffer;
	
	/**
	 * @see application.entity.Offer#lengthDays
	 **/
	public static volatile SingularAttribute<Offer, Integer> lengthDays;
	
	/**
	 * @see application.entity.Offer
	 **/
	public static volatile EntityType<Offer> class_;
	
	/**
	 * @see application.entity.Offer#order
	 **/
	public static volatile SingularAttribute<Offer, Order> order;

}

