package com.yantar.bankingsystem.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(PhoneNumberEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class PhoneNumberEntity_ {

	
	/**
	 * @see com.yantar.bankingsystem.entity.PhoneNumberEntity#number
	 **/
	public static volatile SingularAttribute<PhoneNumberEntity, String> number;
	
	/**
	 * @see com.yantar.bankingsystem.entity.PhoneNumberEntity#id
	 **/
	public static volatile SingularAttribute<PhoneNumberEntity, Long> id;
	
	/**
	 * @see com.yantar.bankingsystem.entity.PhoneNumberEntity
	 **/
	public static volatile EntityType<PhoneNumberEntity> class_;
	
	/**
	 * @see com.yantar.bankingsystem.entity.PhoneNumberEntity#user
	 **/
	public static volatile SingularAttribute<PhoneNumberEntity, UserEntity> user;

	public static final String NUMBER = "number";
	public static final String ID = "id";
	public static final String USER = "user";

}

