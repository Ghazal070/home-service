package entity.users;

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
	 * @see entity.users.Profile#getPassword
	 **/
	public static volatile SingularAttribute<Profile, String> password;
	
	/**
	 * @see entity.users.Profile
	 **/
	public static volatile EmbeddableType<Profile> class_;
	
	/**
	 * @see entity.users.Profile#getEmail
	 **/
	public static volatile SingularAttribute<Profile, String> email;

}

