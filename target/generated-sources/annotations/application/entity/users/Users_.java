package application.entity.users;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@StaticMetamodel(Users.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Users_ extends application.entity.BaseEntity_ {

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String IMAGE = "image";
	public static final String PROFILE = "profile";
	public static final String DATE_TIME_SUBMISSION = "dateTimeSubmission";

	
	/**
	 * @see application.entity.users.Users#firstName
	 **/
	public static volatile SingularAttribute<Users, String> firstName;
	
	/**
	 * @see application.entity.users.Users#lastName
	 **/
	public static volatile SingularAttribute<Users, String> lastName;
	
	/**
	 * @see application.entity.users.Users#image
	 **/
	public static volatile SingularAttribute<Users, Byte[]> image;
	
	/**
	 * @see application.entity.users.Users#profile
	 **/
	public static volatile SingularAttribute<Users, Profile> profile;
	
	/**
	 * @see application.entity.users.Users#dateTimeSubmission
	 **/
	public static volatile SingularAttribute<Users, LocalDateTime> dateTimeSubmission;
	
	/**
	 * @see application.entity.users.Users
	 **/
	public static volatile EntityType<Users> class_;

}

