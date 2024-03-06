package com.yantar.bankingsystem.util.impl;

import com.yantar.bankingsystem.entity.EmailEntity_;
import com.yantar.bankingsystem.entity.PhoneNumberEntity_;
import com.yantar.bankingsystem.entity.UserEntity;
import com.yantar.bankingsystem.entity.UserEntity_;
import com.yantar.bankingsystem.model.UserSearchCriteria;
import com.yantar.bankingsystem.util.UserSpecBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class UserSpecBuilderImpl implements UserSpecBuilder {
    @Override
    public Specification<UserEntity> specFromIdentifier(String identifier) {
        return  idEqualsTo(identifier)
                .or(phoneNumberEqualsTo(identifier))
                .or(emailEqualsTo(identifier));
    }

    @Override
    public Specification<UserEntity> specFromSearchCriteria(UserSearchCriteria criteria) {
        return  nameLike(criteria.name())
                .and(surnameLike(criteria.surname()))
                .and(patronymicLike(criteria.patronymic()))
                .and(emailEqualsTo(criteria.email()))
                .and(phoneNumberEqualsTo(criteria.phoneNumber()))
                .and(olderThen(criteria.birthdate()));

    }

    private Specification<UserEntity> idEqualsTo(String param) {
        return (root, query, builder) -> builder.equal(root.get(UserEntity_.id), param);
    }

    private Specification<UserEntity> nameLike(Optional<String> param) {
        return (root, query, builder) -> param
                    .map(value -> builder.like(root.get(UserEntity_.name), value))
                    .orElse(builder.conjunction());
    }

    private Specification<UserEntity> surnameLike(Optional<String> param) {
        return (root, query, builder) ->  param
                    .map(value -> builder.like(root.get(UserEntity_.surname), value))
                    .orElse(builder.conjunction());
    }

    private Specification<UserEntity> patronymicLike(Optional<String> param) {
        return (root, query, builder) -> param
                    .map(value -> builder.like(root.get(UserEntity_.patronymic), value))
                    .orElse(builder.conjunction());
    }

    private Specification<UserEntity> emailEqualsTo(Optional<String> param) {
        return (root, query, builder) -> param
                    .map(value -> root
                            .join(UserEntity_.emails)
                            .get(EmailEntity_.address).in(List.of(value)))
                    .orElse(builder.conjunction());
    }

    private Specification<UserEntity> emailEqualsTo(String param) {
        return (root, query, builder) -> root
                .join(UserEntity_.emails)
                .get(EmailEntity_.address).in(List.of(param));
    }

    private Specification<UserEntity> phoneNumberEqualsTo(Optional<String> param) {
        return (root, query, builder) -> param
                    .map(value -> root
                            .join(UserEntity_.phoneNumbers)
                            .get(PhoneNumberEntity_.number).in(List.of(value)))
                    .orElse(builder.conjunction());
    }

    private Specification<UserEntity> phoneNumberEqualsTo(String param) {
        return (root, query, builder) -> root
                .join(UserEntity_.phoneNumbers)
                .get(PhoneNumberEntity_.number).in(List.of(param));
    }

    private Specification<UserEntity> olderThen(Optional<LocalDate> param) {
        return (root, query, builder) -> param
                    .map(value -> builder.greaterThan(root.get(UserEntity_.birthdate), value))
                    .orElse(builder.conjunction());
    }
}
