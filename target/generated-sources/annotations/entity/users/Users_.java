package entity.users;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.ZonedDateTime;

@StaticMetamodel(Users.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Users_ extends entity.BaseEntity_ {

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String IMAGE = "image";
	public static final String PROFILE = "profile";
	public static final String DATE_TIME_SUBMISSION = "dateTimeSubmission";

	
	/**
	 * @see entity.users.Users#firstName
	 **/
	public static volatile SingularAttribute<Users, String> firstName;
	
	/**
	 * @see entity.users.Users#lastName
	 **/
	public static volatile SingularAttribute<Users, String> lastName;
	
	/**
	 * @see entity.users.Users#image
	 **/
	public static volatile SingularAttribute<Users, Byte[]> image;
	
	/**
	 * @see entity.users.Users#profile
	 **/
	public static volatile SingularAttribute<Users, Profile> profile;
	
	/**
	 * @see entity.users.Users#dateTimeSubmission
	 **/
	public static volatile SingularAttribute<Users, ZonedDateTime> dateTimeSubmission;
	
	/**
	 * @see entity.users.Users
	 **/
	public static volatile EntityType<Users> class_;

}

