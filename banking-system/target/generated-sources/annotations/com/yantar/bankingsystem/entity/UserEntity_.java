package com.yantar.bankingsystem.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;

@StaticMetamodel(UserEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class UserEntity_ {

	
	/**
	 * @see com.yantar.bankingsystem.entity.UserEntity#emails
	 **/
	public static volatile SetAttribute<UserEntity, EmailEntity> emails;
	
	/**
	 * @see com.yantar.bankingsystem.entity.UserEntity#patronymic
	 **/
	public static volatile SingularAttribute<UserEntity, String> patronymic;
	
	/**
	 * @see com.yantar.bankingsystem.entity.UserEntity#birthdate
	 **/
	public static volatile SingularAttribute<UserEntity, LocalDate> birthdate;
	
	/**
	 * @see com.yantar.bankingsystem.entity.UserEntity#surname
	 **/
	public static volatile SingularAttribute<UserEntity, String> surname;
	
	/**
	 * @see com.yantar.bankingsystem.entity.UserEntity#encodedPassword
	 **/
	public static volatile SingularAttribute<UserEntity, String> encodedPassword;
	
	/**
	 * @see com.yantar.bankingsystem.entity.UserEntity#roles
	 **/
	public static volatile SetAttribute<UserEntity, RoleEntity> roles;
	
	/**
	 * @see com.yantar.bankingsystem.entity.UserEntity#name
	 **/
	public static volatile SingularAttribute<UserEntity, String> name;
	
	/**
	 * @see com.yantar.bankingsystem.entity.UserEntity#id
	 **/
	public static volatile SingularAttribute<UserEntity, Long> id;
	
	/**
	 * @see com.yantar.bankingsystem.entity.UserEntity
	 **/
	public static volatile EntityType<UserEntity> class_;
	
	/**
	 * @see com.yantar.bankingsystem.entity.UserEntity#phoneNumbers
	 **/
	public static volatile SetAttribute<UserEntity, PhoneNumberEntity> phoneNumbers;
	
	/**
	 * @see com.yantar.bankingsystem.entity.UserEntity#account
	 **/
	public static volatile SingularAttribute<UserEntity, AccountEntity> account;

	public static final String EMAILS = "emails";
	public static final String PATRONYMIC = "patronymic";
	public static final String BIRTHDATE = "birthdate";
	public static final String SURNAME = "surname";
	public static final String GRAPH_USER_FULL_GRAPH_ = "User[full-graph]";
	public static final String ENCODED_PASSWORD = "encodedPassword";
	public static final String ROLES = "roles";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String PHONE_NUMBERS = "phoneNumbers";
	public static final String ACCOUNT = "account";

}

