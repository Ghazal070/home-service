package application.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Role.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Role_ extends application.entity.BaseEntity_ {

	public static final String NAME = "name";
	public static final String AUTHORITIES = "authorities";

	
	/**
	 * @see application.entity.Role#name
	 **/
	public static volatile SingularAttribute<Role, String> name;
	
	/**
	 * @see application.entity.Role
	 **/
	public static volatile EntityType<Role> class_;
	
	/**
	 * @see application.entity.Role#authorities
	 **/
	public static volatile SetAttribute<Role, Authority> authorities;

}

