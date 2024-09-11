package entity;

import entity.enumeration.DutyType;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Duty.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Duty_ extends entity.BaseEntity_ {

	public static final String DUTY_TYPE = "dutyType";
	public static final String DESCRIPTION = "description";
	public static final String PARENT_ID = "parentId";
	public static final String BASE_PRICE = "basePrice";

	
	/**
	 * @see entity.Duty#dutyType
	 **/
	public static volatile SingularAttribute<Duty, DutyType> dutyType;
	
	/**
	 * @see entity.Duty#description
	 **/
	public static volatile SingularAttribute<Duty, String> description;
	
	/**
	 * @see entity.Duty
	 **/
	public static volatile EntityType<Duty> class_;
	
	/**
	 * @see entity.Duty#parentId
	 **/
	public static volatile SingularAttribute<Duty, Integer> parentId;
	
	/**
	 * @see entity.Duty#basePrice
	 **/
	public static volatile SingularAttribute<Duty, Integer> basePrice;

}

