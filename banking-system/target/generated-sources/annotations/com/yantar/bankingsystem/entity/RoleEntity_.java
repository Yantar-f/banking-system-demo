package com.yantar.bankingsystem.entity;

import com.yantar.bankingsystem.config.Role;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(RoleEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class RoleEntity_ {

	
	/**
	 * @see com.yantar.bankingsystem.entity.RoleEntity#designation
	 **/
	public static volatile SingularAttribute<RoleEntity, Role> designation;
	
	/**
	 * @see com.yantar.bankingsystem.entity.RoleEntity
	 **/
	public static volatile EntityType<RoleEntity> class_;

	public static final String DESIGNATION = "designation";

}

