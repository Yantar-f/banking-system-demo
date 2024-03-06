package com.yantar.bankingsystem.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(EmailEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class EmailEntity_ {

	
	/**
	 * @see com.yantar.bankingsystem.entity.EmailEntity#address
	 **/
	public static volatile SingularAttribute<EmailEntity, String> address;
	
	/**
	 * @see com.yantar.bankingsystem.entity.EmailEntity#id
	 **/
	public static volatile SingularAttribute<EmailEntity, Long> id;
	
	/**
	 * @see com.yantar.bankingsystem.entity.EmailEntity
	 **/
	public static volatile EntityType<EmailEntity> class_;
	
	/**
	 * @see com.yantar.bankingsystem.entity.EmailEntity#user
	 **/
	public static volatile SingularAttribute<EmailEntity, UserEntity> user;

	public static final String ADDRESS = "address";
	public static final String ID = "id";
	public static final String USER = "user";

}

