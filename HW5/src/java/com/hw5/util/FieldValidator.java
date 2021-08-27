package com.hw5.util;

import com.hw5.model.Person;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Проверяет валидность переданных значений
 */
public class FieldValidator {
    private static final String NUMBER_REGEX =
            "^\\+?[78][-\\(]?\\d{3}\\)?-?\\d{3}-?\\d{2}-?\\d{2}$";
    private static final String ERROR_MSG_EMPTY_FIRST_NAME = "Имя не может быть пустым";
    private static final String ERROR_MSG_FORBIDDEN_CHAR_FIRST_NAME =
            "В имени могут использоваться только буквы";
    private static final String ERROR_MSG_EMPTY_SECOND_NAME = "Фамилия не может быть пустой";
    private static final String ERROR_MSG_FORBIDDEN_CHAR_SECOND_NAME =
            "В фамилии могут использоваться только буквы";
    private static final String ERROR_MSG_FORBIDDEN_CHAR_PATRONYMIC =
            "В отчестве могут использоваться только буквы";
    private static final String ERROR_MSG_FULL_NAME = "Человек с таким ФИО уже существует";
    private static final String ERROR_MSG_TEL = "телефон не соответсвует стандарту";
    private static final String ERROR_MSG_NAME_LEN = "Длинна имени не должна привышать 32 символа";
    private static final String ERROR_MSG_SURNAME_LEN = "Длинна фамилии не должна привышать 32 символа";
    private static final String ERROR_MSG_PATRONYMIC_LEN = "Длинна отчества не должна привышать 32 символа";
    private static final String ERROR_MSG_ADDRESS_LEN = "Длинна адреса не должна привышать 255 символов";
    private static final String ERROR_MSG_COMMENT_LEN = "Длинна комментария не дожна привышать 255 символов";
    private static final String ERROR_MSG_ADDRESS_FORBIDDEN_CHAR = "В адресе нельзя использовать символ \"'\"";
    private static final String ERROR_MSG_COMMENT_FORBIDDEN_CHAR = "В комментарие нельзя использовать символ \"'\"";

    private static final ArrayList<String> errorList = new ArrayList<>(); //Список ошибок

    private static boolean allCharsIsAlpha(String str) {
        for (Character ch: str.toCharArray())
            if (!Character.isAlphabetic(ch) && ch != ' ')
                return false;
        return true;
    }

    /**
     * Добавляет в список ошибок новую ошибку если ее там еще нет
     * @param error новая ошибка
     */
    private static void addToErrorList(String error) {
        if (!errorList.contains(error))
            errorList.add(error);
    }

    /**
     * Проверяет валидность переданного имени
     * @param firstName Имя
     * @return True - значение валидно, False - иначе
     */
    public static boolean isFirstNameValid(String firstName) {
        if (firstName.length() > 32) {
            addToErrorList(ERROR_MSG_NAME_LEN);
            return false;
        }
        if (firstName == null || firstName.isBlank()) {
            addToErrorList(ERROR_MSG_EMPTY_FIRST_NAME);
            return false;
        }
        if (!allCharsIsAlpha(firstName)) {
            addToErrorList(ERROR_MSG_FORBIDDEN_CHAR_FIRST_NAME);
            return false;
        }
        errorList.remove(ERROR_MSG_NAME_LEN);
        errorList.remove(ERROR_MSG_EMPTY_FIRST_NAME);
        errorList.remove(ERROR_MSG_FORBIDDEN_CHAR_FIRST_NAME);
        return true;
    }

    /**
     * Проверяет валидность переданной фамилиии
     * @param secondNameField Фамилия
     * @return True - значение валидно, False - иначе
     */
    public static boolean isSecondNameValid(String secondNameField) {
        if (secondNameField.length() > 32) {
            addToErrorList(ERROR_MSG_SURNAME_LEN);
            return false;
        }
        if (secondNameField == null || secondNameField.isBlank()) {
            addToErrorList(ERROR_MSG_EMPTY_SECOND_NAME);
            return false;
        }
        if (!allCharsIsAlpha(secondNameField)) {
            addToErrorList(ERROR_MSG_FORBIDDEN_CHAR_SECOND_NAME);
            return false;
        }
        errorList.remove(ERROR_MSG_SURNAME_LEN);
        errorList.remove(ERROR_MSG_EMPTY_SECOND_NAME);
        errorList.remove(ERROR_MSG_FORBIDDEN_CHAR_SECOND_NAME);
        return true;
    }

    /**
     * Проверяет валидность переданного отчества
     * @param patronymic отчество
     * @return True - значение валидно, False - иначе
     */
    public static boolean isPatronymicValid(String patronymic) {
        if (patronymic.length() > 32) {
            addToErrorList(ERROR_MSG_PATRONYMIC_LEN);
            return false;
        }
        if (!allCharsIsAlpha(patronymic)) {
            addToErrorList(ERROR_MSG_FORBIDDEN_CHAR_PATRONYMIC);
            return false;
        }
        errorList.remove(ERROR_MSG_FORBIDDEN_CHAR_PATRONYMIC);
        errorList.remove(ERROR_MSG_PATRONYMIC_LEN);
        return true;
    }

    /**
     * Проверяет валидность переданного номера телефона
     * @param tel номер телефона
     * @return True - значение валидно, False - иначе
     */
    public static boolean isTelephoneNumberCorrect(String tel, String telephoneType) {
        if (!tel.isEmpty() && !Pattern.matches(NUMBER_REGEX, tel)) {
            addToErrorList(String.format("%s %s", telephoneType, ERROR_MSG_TEL));
            return false;
        }
        errorList.remove(String.format("%s %s", telephoneType, ERROR_MSG_TEL));
        return true;
    }

    /**
     * Проверяет валидность полного имени
     * @param personData Все пользователи
     * @param person Редактируемый человек
     * @param newFullName Новое полное имя
     * @return True - значение валидно, False - иначе
     */
    public static boolean isFullNameFree(ObservableList<Person> personData, Person person, String newFullName) {
        for (Person p: personData)
            if (!p.equals(person) && p.getFullName().equals(newFullName)) {
                addToErrorList(ERROR_MSG_FULL_NAME);
                return false;
            }
        errorList.remove(ERROR_MSG_FULL_NAME);
        return true;
    }

    /**
     * Проверяет валидность переданного адресса
     * @param address адресс
     * @return True - значение валидно, False - иначе
     */
    public static boolean isAddressCorrect(String address) {
        if (address.length() > 255) {
            addToErrorList(ERROR_MSG_ADDRESS_LEN);
            return false;
        }
        if (address.contains("'")) {
            addToErrorList(ERROR_MSG_ADDRESS_FORBIDDEN_CHAR);
            return false;
        }
        errorList.remove(ERROR_MSG_ADDRESS_LEN);
        errorList.remove(ERROR_MSG_ADDRESS_FORBIDDEN_CHAR);
        return true;
    }

    /**
     * Проверяет валидность переданного комментария
     * @param comment комментарий
     * @return True - значение валидно, False - иначе
     */
    public static boolean isCommentCorrect(String comment) {
        if (comment.length() > 255) {
            addToErrorList(ERROR_MSG_COMMENT_LEN);
            return false;
        }
        if (comment.contains("'")) {
            addToErrorList(ERROR_MSG_COMMENT_FORBIDDEN_CHAR);
            return false;
        }
        errorList.remove(ERROR_MSG_COMMENT_LEN);
        errorList.remove(ERROR_MSG_COMMENT_FORBIDDEN_CHAR);
        return true;
    }

    public static void clearErrorList() {
        errorList.clear();
    }

    /**
     * @return последняя возникшая ошибка
     */
    public static String getLastError() {
        if (errorList.size() > 0)
            return errorList.get(0);
        return "";
    }
}
