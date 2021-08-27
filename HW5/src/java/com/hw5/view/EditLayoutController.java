package com.hw5.view;

import com.hw5.model.Person;
import com.hw5.util.FieldValidator;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Контроллер для окна редактирования
 */
public class EditLayoutController {

    @FXML
    private Label errorLabel; //Label для вывода ошибок
    @FXML
    private DatePicker birthdayField; //Поле для выбора даты рождения
    @FXML
    private TextField addressField; //Поле для ввода адреса
    @FXML
    private TextField homeTelephoneField; //Поле для ввода номера домашнего телефона
    @FXML
    private TextField mobileTelephoneField; //Поле для ввода номера телефона
    @FXML
    private TextField patronymicField; //Поле для ввода отчества
    @FXML
    private TextField secondNameField; //Поле для ввода фамилии
    @FXML
    private TextField firstNameField; //Поле для ввода имени
    @FXML
    private TextArea commentField; //Поле для ввода комментария

    private ObservableList<Person> personData; //Лист с контактами
    private Stage dialogStage; //Родительская сцена
    private Person person; //Редактируемый/добавляемый контакт
    private boolean isSaveClicked = false; //Была ли нажата кнопка Сохранить

    /**
     * Была ли нажата кнопка Сохранить
     *
     * @return True - нажата, False - не нажата
     */
    public boolean getIsSaveClicked() {
        return isSaveClicked;
    }

    /**
     * Устанавливает сцену
     *
     * @param dialogStage сцена
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Инициализатор
     */
    @FXML
    private void initialize() {
        firstNameField.focusedProperty().addListener(this::changeFirstNameHandler);
        secondNameField.focusedProperty().addListener(this::changeSecondNameHandler);
        patronymicField.focusedProperty().addListener(this::changePatronymicHandler);
        mobileTelephoneField.focusedProperty().addListener(this::changeMobileTelephoneHandler);
        homeTelephoneField.focusedProperty().addListener(this::changeHomeTelephoneField);
        addressField.focusedProperty().addListener(this::changeAddressField);
        commentField.focusedProperty().addListener(this::changeCommentField);
        errorLabel.setText("");
    }

    /**
     * Устанавливает изменяемого/добавляемого человека
     *
     * @param person изменяемый/добавляемый человек
     */
    public void setPerson(Person person) {
        this.person = person;

        firstNameField.setText(person.getFirstName());
        secondNameField.setText(person.getSecondName());
        patronymicField.setText(person.getPatronymic());
        mobileTelephoneField.setText(person.getTelephoneNumber());
        homeTelephoneField.setText(person.getHomeTelephoneNumber());
        addressField.setText(person.getAddress());
        commentField.setText(person.getComment());
        birthdayField.setValue(person.getDateOfBirth());
    }

    /**
     * @return полное имя человека, полученное из введенных
     * пользователем значений
     */
    private String getFullName() {
        return String.format("%s %s %s",
                firstNameField.getText().trim(),
                secondNameField.getText().trim(),
                patronymicField.getText().trim()).trim();
    }

    /**
     * Проверяет корректность полного имени
     */
    private void checkFullName() {
        if (!FieldValidator.isFullNameFree(personData, person, getFullName())) {
            firstNameField.setStyle("-fx-border-color: #e74c3c; -fx-focus-color: #e74c3c;");
            secondNameField.setStyle("-fx-border-color: #e74c3c; -fx-focus-color: #e74c3c;");
            patronymicField.setStyle("-fx-border-color: #e74c3c; -fx-focus-color: #e74c3c;");
        } else {
            firstNameField.setStyle("");
            secondNameField.setStyle("");
            patronymicField.setStyle("");
        }
    }

    /**
     * Устанавливает список контактов
     *
     * @param personData список контактов
     */
    public void setPersonData(ObservableList<Person> personData) {
        this.personData = personData;
    }

    /**
     * Обработчик нажатия на кнопку сохранить
     */
    @FXML
    private void handleSave() {
        if (mobileTelephoneField.getText().isBlank() &&
                homeTelephoneField.getText().isBlank()) {
            errorLabel.setText("Необходимо указать хотя бы один телефон");
            mobileTelephoneField.setStyle("-fx-border-color: #e74c3c; -fx-focus-color: #e74c3c;");
            homeTelephoneField.setStyle("-fx-border-color: #e74c3c; -fx-focus-color: #e74c3c;");
        } else {
            if (FieldValidator.getLastError().equals("")) {
                person.setFirstName(firstNameField.getText());
                person.setSecondName(secondNameField.getText());
                person.setPatronymic(patronymicField.getText());
                person.setTelephoneNumber(mobileTelephoneField.getText());
                person.setHomeTelephoneNumber(homeTelephoneField.getText());
                person.setAddress(addressField.getText());
                person.setComment(commentField.getText());
                person.setDateOfBirth(birthdayField.getValue());
                dialogStage.close();
                isSaveClicked = true;
            }
        }
    }

    /**
     * Обработчик нажатия на кнопку
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Проверяет введенные пользователем данные после выхода из
     * поля для ввода имени
     */
    private void changeFirstNameHandler(ObservableValue<? extends Boolean> arg0,
                                        Boolean oldPropertyValue, Boolean newPropertyValue) {
        if (!newPropertyValue) {
            if (!FieldValidator.isFirstNameValid(firstNameField.getText().trim()))
                firstNameField.setStyle("-fx-border-color: #e74c3c; -fx-focus-color: #e74c3c;");
            else {
                firstNameField.setStyle("");
                checkFullName();
            }
            errorLabel.setText(FieldValidator.getLastError());
        }
    }

    /**
     * Проверяет введенные пользователем данные после выхода из
     * поля для ввода фамилии
     */
    private void changeSecondNameHandler(ObservableValue<? extends Boolean> arg0,
                                         Boolean oldPropertyValue, Boolean newPropertyValue) {
        if (!newPropertyValue) {
            if (!FieldValidator.isSecondNameValid(secondNameField.getText().trim()))
                secondNameField.setStyle("-fx-border-color: #e74c3c; -fx-focus-color: #e74c3c;");
            else {
                secondNameField.setStyle("");
                checkFullName();
            }
            errorLabel.setText(FieldValidator.getLastError());
        }
    }

    /**
     * Проверяет введенные пользователем данные после выхода из
     * поля для ввода отчества
     */
    private void changePatronymicHandler(ObservableValue<? extends Boolean> arg0,
                                         Boolean oldPropertyValue, Boolean newPropertyValue) {
        if (!newPropertyValue) {
            if (!FieldValidator.isPatronymicValid(patronymicField.getText().trim()))
                patronymicField.setStyle("-fx-border-color: #e74c3c; -fx-focus-color: #e74c3c;");
            else {
                patronymicField.setStyle("");
                checkFullName();
            }
            errorLabel.setText(FieldValidator.getLastError());
        }
    }

    /**
     * Проверяет введенные пользователем данные после выхода из
     * поля для ввода номера телефона
     */
    private void changeMobileTelephoneHandler(ObservableValue<? extends Boolean> arg0,
                                              Boolean oldPropertyValue, Boolean newPropertyValue) {
        if (!newPropertyValue) {
            if (!FieldValidator.isTelephoneNumberCorrect(mobileTelephoneField.getText(), "Мобильный"))
                mobileTelephoneField.setStyle("-fx-border-color: #e74c3c; -fx-focus-color: #e74c3c;");
            else
                mobileTelephoneField.setStyle("");
            errorLabel.setText(FieldValidator.getLastError());
        }
    }

    /**
     * Проверяет введенные пользователем данные после выхода из
     * поля для ввода домашнего номера телефона
     */
    private void changeHomeTelephoneField(ObservableValue<? extends Boolean> arg0,
                                          Boolean oldPropertyValue, Boolean newPropertyValue) {
        if (!newPropertyValue) {
            if (!FieldValidator.isTelephoneNumberCorrect(homeTelephoneField.getText(), "Домашний"))
                homeTelephoneField.setStyle("-fx-border-color: #e74c3c; -fx-focus-color: #e74c3c;");
            else
                homeTelephoneField.setStyle("");
            errorLabel.setText(FieldValidator.getLastError());
        }
    }

    private void changeAddressField(ObservableValue<? extends Boolean> arg0,
                                    Boolean oldPropertyValue, Boolean newPropertyValue) {
        if (!newPropertyValue) {
            if (!FieldValidator.isAddressCorrect(addressField.getText()))
                addressField.setStyle("-fx-border-color: #e74c3c; -fx-focus-color: #e74c3c;");
            else
                addressField.setStyle("");
            errorLabel.setText(FieldValidator.getLastError());
        }
    }

    private void changeCommentField(ObservableValue<? extends Boolean> arg0,
                                    Boolean oldPropertyValue, Boolean newPropertyValue) {
        if (!newPropertyValue) {
            if (!FieldValidator.isCommentCorrect(commentField.getText()))
                commentField.setStyle("-fx-border-color: #e74c3c; -fx-focus-color: #e74c3c;");
            else
                commentField.setStyle("");
            errorLabel.setText(FieldValidator.getLastError());
        }
    }
}
