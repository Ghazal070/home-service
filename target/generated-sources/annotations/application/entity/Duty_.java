package application.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Duty.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Duty_ extends application.entity.BaseEntity_ {

	public static final String DUTY_TYPE = "dutyType";
	public static final String DESCRIPTION = "description";
	public static final String PARENT_ID = "parentId";
	public static final String BASE_PRICE = "basePrice";

	
	/**
	 * @see application.entity.Duty#dutyType
	 **/
	public static volatile SingularAttribute<Duty, DutyType> dutyType;
	
	/**
	 * @see application.entity.Duty#description
	 **/
	public static volatile SingularAttribute<Duty, String> description;
	
	/**
	 * @see application.entity.Duty
	 **/
	public static volatile EntityType<Duty> class_;
	
	/**
	 * @see application.entity.Duty#parentId
	 **/
	public static volatile SingularAttribute<Duty, Integer> parentId;
	
	/**
	 * @see application.entity.Duty#basePrice
	 **/
	public static volatile SingularAttribute<Duty, Integer> basePrice;

}

