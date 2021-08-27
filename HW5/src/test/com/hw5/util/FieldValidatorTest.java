package com.hw5.util;

import com.hw5.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FieldValidatorTest {

    //Тестовый лист с контактами
    private ObservableList<Person> testList = FXCollections.observableArrayList(
            new Person("Lol", "kek", ""),
            new Person("Lol", "kek", "Cheburek"),
            new Person("Lol1", "kek", "")
    );

    @BeforeEach
    void setUp() {
        FieldValidator.clearErrorList();
    }

    /**
     * Проверяет, работоспособность метода isFirstNameValid
     * на корректных именах
     */
    @Test
    void isFirstNameValid_CorrectName_TrueAndErrorListEmpty() {
        ArrayList<String> names = new ArrayList<>(Arrays.asList(
                "Вася",
                "Никита",
                "Данила",
                "Рамон Антонио",
                "Дима"
        ));
        for (String name: names) {
            assertTrue(FieldValidator.isFirstNameValid(name));
            assertEquals(FieldValidator.getLastError(), "");
        }
    }

    /**
     * Проверяет, работоспособность метода isFirstNameValid
     * на пустых именах
     */
    @Test
    void isFirstNameValid_EmptyName_FalseAndErrorMsg() {
        ArrayList<String> names = new ArrayList<>(Arrays.asList(
                "",
                " ",
                "        "
        ));
        for (String name: names) {
            assertFalse(FieldValidator.isFirstNameValid(name));
            assertEquals(FieldValidator.getLastError(), "Имя не может быть пустым");
        }
    }

    /**
     * Проверяет, работоспособность метода isFirstNameValid
     * на именах с некорректными символами
     */
    @Test
    void isFirstNameValid_ForbiddenChar_FalseAndErrorMsg() {
        ArrayList<String> names = new ArrayList<>(Arrays.asList(
                "Алек%сей",
                "Ирина,",
                "Кто я?"
        ));
        for (String name: names) {
            assertFalse(FieldValidator.isFirstNameValid(name));
            assertEquals(FieldValidator.getLastError(), "В имени могут использоваться только буквы");
        }
    }

    /**
     * Проверяет, работоспособность метода isSecondNameValid
     * на корректных фамилиях
     */
    @Test
    void isSecondNameValid_CorrectSecondName_TrueAndErrorListEmpty() {
        ArrayList<String> secondNames = new ArrayList<>(Arrays.asList(
                "Иванов",
                "Петров",
                "Кузнецов",
                "Родригес Залепинос",
                "Не фемилия"
        ));
        for (String name: secondNames) {
            assertTrue(FieldValidator.isSecondNameValid(name));
            assertEquals(FieldValidator.getLastError(), "");
        }
    }

    /**
     * Проверяет, работоспособность метода isSecondNameValid
     * на пустых фамилиях
     */
    @Test
    void isSecondNameValid_EmptySecondName_FalseAndErrorMsg() {
        ArrayList<String> secondNames = new ArrayList<>(Arrays.asList(
                "",
                " ",
                "        "
        ));
        for (String name: secondNames) {
            assertFalse(FieldValidator.isSecondNameValid(name));
            assertEquals(FieldValidator.getLastError(), "Фамилия не может быть пустой");
        }
    }

    /**
     * Проверяет, работоспособность метода isSecondNameValid
     * на фамилиях с некорректными символами
     */
    @Test
    void isSecondNameValid_ForbiddenChar_FalseAndErrorMsg() {
        ArrayList<String> secondNames = new ArrayList<>(Arrays.asList(
                "Иван%сей",
                "Шиз*ид,",
                "Кто я?"
        ));
        for (String name: secondNames) {
            assertFalse(FieldValidator.isSecondNameValid(name));
            assertEquals(FieldValidator.getLastError(), "В фамилии могут использоваться только буквы");
        }
    }

    /**
     * Проверяет, работоспособность метода isPatronymicValid
     * на корректных отчествах
     */
    @Test
    void isPatronymic_CorrectPatronymic_TrueAndErrorListEmpty() {
        ArrayList<String> patronymics = new ArrayList<>(Arrays.asList(
                "Иванович",
                "Петровииич",
                "",
                "Родригес Залепинос",
                "Не отчество"
        ));
        for (String name: patronymics) {
            assertTrue(FieldValidator.isPatronymicValid(name));
            assertEquals(FieldValidator.getLastError(), "");
        }
    }

    /**
     * Проверяет, работоспособность метода isPatronymicValid
     * на отчествах с запрещенными символами
     */
    @Test
    void isPatronymicValid_ForbiddenChar_FalseAndErrorMsg() {
        ArrayList<String> patronymics = new ArrayList<>(Arrays.asList(
                "Иван%сей",
                "Шиз*ид,",
                "Кто я?"
        ));
        for (String name: patronymics) {
            assertFalse(FieldValidator.isPatronymicValid(name));
            assertEquals(FieldValidator.getLastError(), "В отчестве могут использоваться только буквы");
        }
    }

    /**
     * Проверяет, работоспособность метода isTelephoneNumberCorrect
     * на корректных номерах телефона
     */
    @Test
    void isTelephoneNumberCorrect_CorrectNumber_True() {
        ArrayList<String> numbers = new ArrayList<>(Arrays.asList(
                "+78005553535",
                "88005553535",
                "8(800)5553535",
                "8-800-555-35-35"
        ));
        for (String number: numbers) {
            assertTrue(FieldValidator.isTelephoneNumberCorrect(number, ""));
            assertEquals(FieldValidator.getLastError(), "");
        }
    }

    /**
     * Проверяет, работоспособность метода isTelephoneNumberCorrect
     * на некорректных номерах телефона
     */
    @Test
    void isTelephoneNumberCorrect_IncorrectNumber_False() {
        ArrayList<String> numbers = new ArrayList<>(Arrays.asList(
                "8800555353333455",
                "8(800)(555)3535",
                "-8800555-35-35",
                "123",
                "+7(800)-35-35",
                "880053-3535-335"
        ));
        for (String number: numbers)
            assertFalse(FieldValidator.isTelephoneNumberCorrect(number, ""));
    }

    /**
     * Проверяет, работоспособность метода isFullNameFree
     * на свободном имени
     */
    @Test
    void isFullNameFree_FreeFullName_True() {
        String newFullName = "Шпоть Валерий Евгеньевич";
        assertTrue(FieldValidator.isFullNameFree(testList, new Person(), newFullName));
    }

    /**
     * Проверяет, работоспособность метода isFullNameFree
     * на занятом имени
     */
    @Test
    void isFullNameFree_NotFreeFullName_False() {
        String newFullName = "Lol kek";
        assertFalse(FieldValidator.isFullNameFree(testList, new Person(), newFullName));
    }
}