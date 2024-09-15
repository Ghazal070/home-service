package entity.users;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.ZonedDateTime;

@StaticMetamodel(User.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class User_ extends entity.BaseEntity_ {

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String IMAGE = "image";
	public static final String PROFILE = "profile";
	public static final String DATE_TIME_SUBMISSION = "dateTimeSubmission";

	
	/**
	 * @see entity.users.User#firstName
	 **/
	public static volatile SingularAttribute<User, String> firstName;
	
	/**
	 * @see entity.users.User#lastName
	 **/
	public static volatile SingularAttribute<User, String> lastName;
	
	/**
	 * @see entity.users.User#image
	 **/
	public static volatile SingularAttribute<User, Byte[]> image;
	
	/**
	 * @see entity.users.User#profile
	 **/
	public static volatile SingularAttribute<User, Profile> profile;
	
	/**
	 * @see entity.users.User#dateTimeSubmission
	 **/
	public static volatile SingularAttribute<User, ZonedDateTime> dateTimeSubmission;
	
	/**
	 * @see entity.users.User
	 **/
	public static volatile EntityType<User> class_;

}

