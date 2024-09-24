package application.entity.users;

import application.entity.Duty;
import application.entity.enumeration.ExpertStatus;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Expert.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Expert_ extends application.entity.users.Users_ {

	public static final String SCORE = "score";
	public static final String EXPERT_STATUS = "expertStatus";
	public static final String DUTIES = "duties";

	
	/**
	 * @see application.entity.users.Expert#score
	 **/
	public static volatile SingularAttribute<Expert, Integer> score;
	
	/**
	 * @see application.entity.users.Expert#expertStatus
	 **/
	public static volatile SingularAttribute<Expert, ExpertStatus> expertStatus;
	
	/**
	 * @see application.entity.users.Expert
	 **/
	public static volatile EntityType<Expert> class_;
	
	/**
	 * @see application.entity.users.Expert#duties
	 **/
	public static volatile SetAttribute<Expert, Duty> duties;

}

