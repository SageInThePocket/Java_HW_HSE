package com.hw5.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

//Модель человека
public class Person implements Serializable {

    private int id = -1;
    private String firstName; //Имя
    private String secondName; //Фамилия
    private String patronymic; //Отчество
    private String telephoneNumber; //Мобильный телефон
    private String homeTelephoneNumber; //Домашний телефон
    private String address; //Адрес
    private LocalDate dateOfBirth; //Дата рождения
    private String comment; //Комметарий

    /**
     * Создает пустого человека
     */
    public Person() {
        firstName = "";
        secondName = "";
        patronymic = "";
        telephoneNumber = "";
        homeTelephoneNumber = "";
        address = "";
        dateOfBirth = LocalDate.now();
        comment = "";
    }

    /**
     * Создает человека с ФИО
     */
    public Person(String firstName, String secondName, String patronymic) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.patronymic = patronymic;
        telephoneNumber = "";
        homeTelephoneNumber = "";
        address = "";
        dateOfBirth = LocalDate.now();
        comment = "";
    }

    /**
     * Создает контакт, заполняя все поля
     * @param firstName Имя
     * @param secondName Фамилия
     * @param patronymic Отчество
     * @param telephoneNumber Номер телефона
     * @param homeTelephoneNumber Домашний номер телефона
     * @param address Адресс
     * @param dateOfBirth Дата рождения
     * @param comment Комментарий к контакту
     */
    public Person(String firstName, String secondName, String patronymic,
                  String telephoneNumber, String homeTelephoneNumber, String address,
                  Date dateOfBirth, String comment) {
        this();
        if (firstName == null && secondName == null)
            throw new IllegalArgumentException("Name and surname cannot be null");
        this.firstName = firstName;
        this.secondName = secondName;
        if (patronymic != null)
            this.patronymic = patronymic;
        if (telephoneNumber != null)
            this.telephoneNumber = telephoneNumber;
        if (homeTelephoneNumber != null)
            this.homeTelephoneNumber = homeTelephoneNumber;
        if (address != null)
            this.address = address;
        if (dateOfBirth != null)
            this.dateOfBirth = dateOfBirth.toLocalDate();
        else
            this.dateOfBirth = LocalDate.now();
        if (comment != null)
            this.comment = comment;
    }

    /**
     * @return имя
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return фамилия
     */
    public String getSecondName() {
        return secondName;
    }

    /**
     * @return отчество
     */
    public String getPatronymic() {
        return patronymic;
    }

    /**
     * @return мобильный телефон
     */
    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    /**
     * @return адрес
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return дата рождения
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * @return комментарии
     */
    public String getComment() {
        return comment;
    }

    /**
     * @return домашний телефон
     */
    public String getHomeTelephoneNumber() {
        return homeTelephoneNumber;
    }

    /**
     * @return свойство имени
     */
    public StringProperty getFirstNameProperty() {
        return new SimpleStringProperty(firstName);
    }

    /**
     * @return свойство фамилии
     */
    public StringProperty getSecondNameProperty() {
        return new SimpleStringProperty(secondName);
    }

    /**
     * @return свойство отчества
     */
    public StringProperty getPatronymicProperty() {
        return new SimpleStringProperty(patronymic);
    }

    /**
     * @return свойство мобильного телефона
     */
    public StringProperty getTelephoneNumberProperty() {
        return new SimpleStringProperty(telephoneNumber);
    }

    /**
     * @return свойство адреса
     */
    public StringProperty getAddressProperty() {
        return new SimpleStringProperty(address);
    }

    /**
     * @return свойство даты рождения
     */
    public ObjectProperty<LocalDate> getDateOfBirthProperty() {
        return new SimpleObjectProperty<>(dateOfBirth);
    }

    /**
     * @return свойство комметария
     */
    public StringProperty getCommentProperty() {
        return new SimpleStringProperty(comment);
    }

    /**
     * @return свойство домашнего телефона
     */
    public StringProperty homeTelephoneNumberProperty() {
        return new SimpleStringProperty(homeTelephoneNumber);
    }

    /**
     * @param value новое значение имени
     */
    public void setFirstName(String value) {
        firstName = value;
    }

    /**
     * @param value новое значение фамилии
     */
    public void setSecondName(String value) {
        secondName = value;
    }

    /**
     * @param value новое значение отчества
     */
    public void setPatronymic(String value) {
        patronymic = value;
    }

    /**
     * @param value новое значение мобильного телефона
     */
    public void setTelephoneNumber(String value) {
        telephoneNumber = value;
    }

    /**
     * @param value новое значение адреса
     */
    public void setAddress(String value) {
        address = value;
    }

    /**
     * @param value новое значение даты рождения
     */
    public void setDateOfBirth(LocalDate value) {
        dateOfBirth = value;
    }

    /**
     * @param value новое значение комментария
     */
    public void setComment(String value) {
        comment = value;
    }

    /**
     * @param value новое значение домашнего телефона
     */
    public void setHomeTelephoneNumber(String value) {
        homeTelephoneNumber = value;
    }

    /**
     * @return Полное имя человека
     */
    public String getFullName() {
        return String.format("%s %s %s",
                getFirstName().trim(),
                getSecondName().trim(),
                getPatronymic().trim()).trim();
    }

    /**
     * Проверяет наличие имени и фамилии у контакта
     */
    public boolean withoutNameAndSurname() {
        return firstName.isBlank() || secondName.isBlank();
    }

    /**
     * Проверяет наличии хотя бы одного номера телефона
     */
    public boolean hasAtLeastOneTelephone() {
        return !telephoneNumber.isBlank() || !homeTelephoneNumber.isBlank();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Person) {
            Person other = (Person) obj;
            return getFullName().equals(other.getFullName());
        }
        return super.equals(obj);
    }

    /**
     * Проверяет является ли переданный контакт точной копией данного объекта
     * @param person сравниваемый контакт
     * @return True - контакты одинаковые, False - разные
     */
    public boolean isCopy(Person person) {
        return firstName.equals(person.firstName) && secondName.equals(person.secondName) &&
                patronymic.equals(person.patronymic) && telephoneNumber.equals(person.telephoneNumber) &&
                homeTelephoneNumber.equals(person.homeTelephoneNumber) && address.equals(person.address) &&
                comment.equals(person.comment);
    }

    @Override
    public String toString() {
        return "Person:" +
                "\nid=" + id +
                "\nfirstName='" + firstName + '\'' +
                "\nsecondName='" + secondName + '\'' +
                "\npatronymic='" + patronymic + '\'' +
                "\ntelephoneNumber='" + telephoneNumber + '\'' +
                "\nhomeTelephoneNumber='" + homeTelephoneNumber + '\'' +
                "\naddress='" + address + '\'' +
                "\ndateOfBirth=" + dateOfBirth +
                "\ncomment='" + comment + '\'';
    }

    /**
     * @return id контакта в таблице, где он хранится
     */
    public int getId() {
        return id;
    }

    /**
     * @param id новое значение id
     */
    public void setId(int id) {
        this.id = id;
    }
}
