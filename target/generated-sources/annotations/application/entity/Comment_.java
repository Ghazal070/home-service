package application.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Comment.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Comment_ extends application.entity.BaseEntity_ {

	public static final String OFFER = "offer";
	public static final String CONTENT = "content";

	
	/**
	 * @see application.entity.Comment#offer
	 **/
	public static volatile SingularAttribute<Comment, Offer> offer;
	
	/**
	 * @see application.entity.Comment
	 **/
	public static volatile EntityType<Comment> class_;
	
	/**
	 * @see application.entity.Comment#content
	 **/
	public static volatile SingularAttribute<Comment, String> content;

}

