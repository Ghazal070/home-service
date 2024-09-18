package entity.users;

import entity.enumeration.ExpertStatus;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Expert.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Expert_ extends entity.users.Users_ {

	public static final String SCORE = "score";
	public static final String EXPERT_STATUS = "expertStatus";

	
	/**
	 * @see entity.users.Expert#score
	 **/
	public static volatile SingularAttribute<Expert, Integer> score;
	
	/**
	 * @see entity.users.Expert#expertStatus
	 **/
	public static volatile SingularAttribute<Expert, ExpertStatus> expertStatus;
	
	/**
	 * @see entity.users.Expert
	 **/
	public static volatile EntityType<Expert> class_;

}

