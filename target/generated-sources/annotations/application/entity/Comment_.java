package application.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Comment.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Comment_ extends application.entity.BaseEntity_ {

	public static final String CONTENT = "content";
	public static final String ORDER = "order";

	
	/**
	 * @see application.entity.Comment
	 **/
	public static volatile EntityType<Comment> class_;
	
	/**
	 * @see application.entity.Comment#content
	 **/
	public static volatile SingularAttribute<Comment, String> content;
	
	/**
	 * @see application.entity.Comment#order
	 **/
	public static volatile SingularAttribute<Comment, Order> order;

}

