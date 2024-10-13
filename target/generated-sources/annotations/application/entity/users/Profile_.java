package application.entity.users;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EmbeddableType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Profile.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Profile_ {

	public static final String PASSWORD = "password";
	public static final String EMAIL = "email";

	
	/**
	 * @see application.entity.users.Profile#password
	 **/
	public static volatile SingularAttribute<Profile, String> password;
	
	/**
	 * @see application.entity.users.Profile
	 **/
	public static volatile EmbeddableType<Profile> class_;
	
	/**
	 * @see application.entity.users.Profile#email
	 **/
	public static volatile SingularAttribute<Profile, String> email;

}

