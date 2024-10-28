package application.entity.users;

import application.entity.Credit;
import application.entity.Order;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Customer.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Customer_ extends application.entity.users.Users_ {

	public static final String ORDERS = "orders";
	public static final String CREDIT = "credit";

	
	/**
	 * @see application.entity.users.Customer#orders
	 **/
	public static volatile SetAttribute<Customer, Order> orders;
	
	/**
	 * @see application.entity.users.Customer#credit
	 **/
	public static volatile SingularAttribute<Customer, Credit> credit;
	
	/**
	 * @see application.entity.users.Customer
	 **/
	public static volatile EntityType<Customer> class_;

}

