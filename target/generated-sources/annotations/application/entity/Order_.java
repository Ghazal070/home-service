package application.entity;

import application.entity.enumeration.OrderStatus;
import application.entity.users.Customer;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@StaticMetamodel(Order.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Order_ extends application.entity.BaseEntity_ {

	public static final String OFFERS = "offers";
	public static final String PRICE_ORDER = "priceOrder";
	public static final String ADDRESS = "address";
	public static final String DATE_TIME_ORDER = "dateTimeOrder";
	public static final String DESCRIPTION = "description";
	public static final String ORDER_STATUS = "orderStatus";
	public static final String DUTY = "duty";
	public static final String CUSTOMER = "customer";

	
	/**
	 * @see application.entity.Order#offers
	 **/
	public static volatile SetAttribute<Order, Offer> offers;
	
	/**
	 * @see application.entity.Order#priceOrder
	 **/
	public static volatile SingularAttribute<Order, Integer> priceOrder;
	
	/**
	 * @see application.entity.Order#address
	 **/
	public static volatile SingularAttribute<Order, String> address;
	
	/**
	 * @see application.entity.Order#dateTimeOrder
	 **/
	public static volatile SingularAttribute<Order, LocalDateTime> dateTimeOrder;
	
	/**
	 * @see application.entity.Order#description
	 **/
	public static volatile SingularAttribute<Order, String> description;
	
	/**
	 * @see application.entity.Order#orderStatus
	 **/
	public static volatile SingularAttribute<Order, OrderStatus> orderStatus;
	
	/**
	 * @see application.entity.Order#duty
	 **/
	public static volatile SingularAttribute<Order, Duty> duty;
	
	/**
	 * @see application.entity.Order
	 **/
	public static volatile EntityType<Order> class_;
	
	/**
	 * @see application.entity.Order#customer
	 **/
	public static volatile SingularAttribute<Order, Customer> customer;

}
