package entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.MappedSuperclassType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.io.Serializable;

@StaticMetamodel(BaseEntity.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class BaseEntity_ {

	public static final String ID = "id";

	
	/**
	 * @see entity.BaseEntity#id
	 **/
	public static volatile SingularAttribute<BaseEntity, Serializable> id;
	
	/**
	 * @see entity.BaseEntity
	 **/
	public static volatile MappedSuperclassType<BaseEntity> class_;

}

