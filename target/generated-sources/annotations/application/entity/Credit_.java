package application.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Credit.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Credit_ extends application.entity.BaseEntity_ {

	public static final String AMOUNT = "amount";

	
	/**
	 * @see application.entity.Credit#amount
	 **/
	public static volatile SingularAttribute<Credit, Integer> amount;
	
	/**
	 * @see application.entity.Credit
	 **/
	public static volatile EntityType<Credit> class_;

}

