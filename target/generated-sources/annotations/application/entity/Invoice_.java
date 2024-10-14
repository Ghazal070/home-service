package application.entity;

import application.entity.enumeration.PaymentType;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@StaticMetamodel(Invoice.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Invoice_ extends application.entity.BaseEntity_ {

	public static final String ORDER_ID = "orderId";
	public static final String LAST_UPDATE = "lastUpdate";
	public static final String CUSTOMER_ID = "customerId";
	public static final String OFFER_ID = "offerId";
	public static final String PAYMENT_TYPE = "paymentType";
	public static final String CREATE_DATE = "createDate";

	
	/**
	 * @see application.entity.Invoice#orderId
	 **/
	public static volatile SingularAttribute<Invoice, Integer> orderId;
	
	/**
	 * @see application.entity.Invoice#lastUpdate
	 **/
	public static volatile SingularAttribute<Invoice, LocalDateTime> lastUpdate;
	
	/**
	 * @see application.entity.Invoice#customerId
	 **/
	public static volatile SingularAttribute<Invoice, Integer> customerId;
	
	/**
	 * @see application.entity.Invoice#offerId
	 **/
	public static volatile SingularAttribute<Invoice, Integer> offerId;
	
	/**
	 * @see application.entity.Invoice
	 **/
	public static volatile EntityType<Invoice> class_;
	
	/**
	 * @see application.entity.Invoice#paymentType
	 **/
	public static volatile SingularAttribute<Invoice, PaymentType> paymentType;
	
	/**
	 * @see application.entity.Invoice#createDate
	 **/
	public static volatile SingularAttribute<Invoice, LocalDateTime> createDate;

}

