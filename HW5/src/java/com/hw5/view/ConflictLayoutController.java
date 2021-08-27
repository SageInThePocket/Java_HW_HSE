package com.hw5.view;

import com.hw5.model.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;

/**
 * Контроллер окна, возникающего при открытии возникновении
 * конфликта во время импортирования данных
 */
public class ConflictLayoutController {

    @FXML
    private Label name1Label; //Имя контакта из телефонной книги
    @FXML
    private Label secondName1Label; //Фамилия контакта из телефонной книги
    @FXML
    private Label patronymic1Label; //Отчество контакта из телефонной книги
    @FXML
    private Label tel1Label; //Мобильный телефон контакта из телефонной книги
    @FXML
    private Label homeTel1Label; //Домашний телефон контакта из телефонной книги
    @FXML
    private Label birthday1Label; //Дата рождения контакта из телефонной книги
    @FXML
    private Label name2Label; //Имя импортируемого контакта
    @FXML
    private Label secondName2Label; //Фамилия импортируемого контакта
    @FXML
    private Label patronymic2Label; //Отчество импортируемого контакта
    @FXML
    private Label tel2Label; //Мобильный телефон импортируемого контакта
    @FXML
    private Label homeTel2Label; //Домашний телефон импортируемого контакта
    @FXML
    private Label birthday2Label; //Дата рождения импортируемого контакта

    private Stage dialogStage; //Родительский сцены
    private boolean isReplaceBtnClick = false; //Нажата ли кнопка Заменить

    /**
     * @return нажата ли кнопка Заменить
     */
    public boolean getIsReplaceBtnClick() {
        return isReplaceBtnClick;
    }

    /**
     * Устанавливает значение родительской сцены
     * @param stage родительская сцена
     */
    public void setDialogStage(Stage stage) {
        dialogStage = stage;
    }

    /**
     * Устанавливает контакт из телефонной книги
     * @param person контакт из телефонной книги
     */
    public void setPersonFromBook(Person person) {
        name1Label.setText(person.getFirstName());
        secondName1Label.setText(person.getSecondName());
        patronymic1Label.setText(person.getPatronymic());
        tel1Label.setText(person.getTelephoneNumber());
        homeTel1Label.setText(person.getHomeTelephoneNumber());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dataStr = formatter.format(person.getDateOfBirth());
        birthday1Label.setText(dataStr);
    }

    /**
     * Устанавливает импортируемый контакт
     * @param person импортируемый контакт
     */
    public void setPersonFromFile(Person person) {
        name2Label.setText(person.getFirstName());
        secondName2Label.setText(person.getSecondName());
        patronymic2Label.setText(person.getPatronymic());
        tel2Label.setText(person.getTelephoneNumber());
        homeTel2Label.setText(person.getHomeTelephoneNumber());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dataStr = formatter.format(person.getDateOfBirth());
        birthday2Label.setText(dataStr);
    }

    /**
     * Обработчик нажатия на кнопку Заменить
     */
    @FXML
    private void handleReplaceBtn() {
        isReplaceBtnClick = true;
        dialogStage.close();
    }

    /**
     * Обработчик нажатия на кнопку Пропустить
     */
    @FXML
    private void handleSkipBtn() {
        dialogStage.close();
    }

}
