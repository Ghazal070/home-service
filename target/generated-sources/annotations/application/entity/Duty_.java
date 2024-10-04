package application.entity;

import application.entity.users.Expert;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Duty.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Duty_ extends application.entity.BaseEntity_ {

	public static final String PARENT = "parent";
	public static final String SELECTABLE = "selectable";
	public static final String DESCRIPTION = "description";
	public static final String TITLE = "title";
	public static final String EXPERTS = "experts";
	public static final String BASE_PRICE = "basePrice";
	public static final String SUB_DUTY = "subDuty";

	
	/**
	 * @see application.entity.Duty#parent
	 **/
	public static volatile SingularAttribute<Duty, Duty> parent;
	
	/**
	 * @see application.entity.Duty#selectable
	 **/
	public static volatile SingularAttribute<Duty, Boolean> selectable;
	
	/**
	 * @see application.entity.Duty#description
	 **/
	public static volatile SingularAttribute<Duty, String> description;
	
	/**
	 * @see application.entity.Duty#title
	 **/
	public static volatile SingularAttribute<Duty, String> title;
	
	/**
	 * @see application.entity.Duty
	 **/
	public static volatile EntityType<Duty> class_;
	
	/**
	 * @see application.entity.Duty#experts
	 **/
	public static volatile SetAttribute<Duty, Expert> experts;
	
	/**
	 * @see application.entity.Duty#basePrice
	 **/
	public static volatile SingularAttribute<Duty, Integer> basePrice;
	
	/**
	 * @see application.entity.Duty#subDuty
	 **/
	public static volatile SetAttribute<Duty, Duty> subDuty;

}

