package application.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(DutyType.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class DutyType_ extends application.entity.BaseEntity_ {

	public static final String TITLE = "title";

	
	/**
	 * @see application.entity.DutyType#title
	 **/
	public static volatile SingularAttribute<DutyType, String> title;
	
	/**
	 * @see application.entity.DutyType
	 **/
	public static volatile EntityType<DutyType> class_;

}

