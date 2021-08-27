package com.hw5.util;

import com.hw5.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Фильтрует лист контактов по ФИО
 */
public class FullNameFilter {
    private ArrayList<String> filterWords = new ArrayList<>();
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    /**
     * Устанавливает значение слов для фильтрации
     * @param filter фильтр
     */
    public void setFilter(String filter) {
        filter = filter.toLowerCase();
        filterWords = new ArrayList<>(Arrays.asList(filter.split(" ")));
    }

    /**
     * Устанавливает список контактов
     * @param personData новый список контактов
     */
    public void setPersonData(ObservableList<Person> personData) {
        this.personData = personData;
    }

    /**
     * Возвращает отфильтрованный лист
     * @return отфильтрованный лист
     */
    public ObservableList<Person> filterPersonData() {
        return FXCollections.observableArrayList(
                personData.filtered(person -> {
                    for (String word : filterWords)
                        if (!person.getFullName().toLowerCase().contains(word))
                            return false;
                    return true;
                })
        );
    }

}
