package com.bankingsystem.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users",

        indexes = {
                @Index(name = "name_index",         columnList = "name"),
                @Index(name = "surname_index",      columnList = "surname"),
                @Index(name = "patronymic_index",   columnList = "patronymic"),
                @Index(name = "birthdate_index",    columnList = "birthdate")
        })
@NamedEntityGraphs({
        @NamedEntityGraph(name = UserEntity.FULL_GRAPH,

                attributeNodes = {
                        @NamedAttributeNode("emails"),
                        @NamedAttributeNode("phoneNumbers"),
                        @NamedAttributeNode("account")
                })
})
public class UserEntity {
    public static final String FULL_GRAPH = "User[full-graph]";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotNull
    @NotBlank
    @Column(name = "name")
    private String name;

    @NotNull
    @NotBlank
    @Column(name = "surname")
    private String surname;

    @NotNull
    @NotBlank
    @Column(name = "patronymic")
    private String patronymic;

    @NotNull
    @Column(name = "birthdate")
    private LocalDate birthdate;

    @NotNull
    @NotBlank
    @Column(name = "password")
    private String encodedPassword;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private Set<EmailEntity> emails = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private Set<PhoneNumberEntity> phoneNumbers = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.PERSIST)
    private AccountEntity account;

    public static UserEntity asReference(Long id) {
        UserEntity user = new UserEntity();
        user.setId(id);

        return user;
    }

    protected UserEntity() {}

    public UserEntity(String name,
                      String surname,
                      String patronymic,
                      LocalDate birthdate,
                      String encodedPassword) {

        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthdate = birthdate;
        this.encodedPassword = encodedPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public Set<EmailEntity> getEmails() {
        return emails;
    }

    public void setEmails(Set<EmailEntity> emails) {
        this.emails = emails;
    }

    public void addEmail(EmailEntity email) {
        emails.add(email);
    }

    public Set<PhoneNumberEntity> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Set<PhoneNumberEntity> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public void addPhoneNumber(PhoneNumberEntity phoneNumber) {
        phoneNumbers.add(phoneNumber);
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }
}
