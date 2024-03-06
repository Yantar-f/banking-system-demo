package com.yantar.bankingsystem.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(AccountEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class AccountEntity_ {

	
	/**
	 * @see com.yantar.bankingsystem.entity.AccountEntity#amount
	 **/
	public static volatile SingularAttribute<AccountEntity, Long> amount;
	
	/**
	 * @see com.yantar.bankingsystem.entity.AccountEntity#id
	 **/
	public static volatile SingularAttribute<AccountEntity, Long> id;
	
	/**
	 * @see com.yantar.bankingsystem.entity.AccountEntity
	 **/
	public static volatile EntityType<AccountEntity> class_;
	
	/**
	 * @see com.yantar.bankingsystem.entity.AccountEntity#user
	 **/
	public static volatile SingularAttribute<AccountEntity, UserEntity> user;

	public static final String AMOUNT = "amount";
	public static final String ID = "id";
	public static final String USER = "user";

}

