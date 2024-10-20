package application.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Authority.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Authority_ extends application.entity.BaseEntity_ {

	public static final String NAME = "name";

	
	/**
	 * @see application.entity.Authority#name
	 **/
	public static volatile SingularAttribute<Authority, String> name;
	
	/**
	 * @see application.entity.Authority
	 **/
	public static volatile EntityType<Authority> class_;

}

