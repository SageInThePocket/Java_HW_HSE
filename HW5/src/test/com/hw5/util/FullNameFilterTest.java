package com.hw5.util;

import com.hw5.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FullNameFilterTest {

    private final FullNameFilter filter = new FullNameFilter();
    private final ObservableList<Person> testList = FXCollections.observableArrayList(
            new Person("Lol", "kek", ""),
            new Person("Lol", "kek", "Cheburek"),
            new Person("Lol1", "kek", ""),
            new Person("sdfsdfsdf", "sdfsdfsdf", "lll")
    );

    /**
     * Проверяет правильность отфильтрованных данных
     * @param checkableList отфильтрованный лист, который проверяем
     * @param filter фильтр по которому происходила фильтрация
     * @return True - правильная фильтрация, False - неправильная
     */
    private boolean checkCorrect(ObservableList<Person> checkableList, String filter) {
        ArrayList<String> filterWords = new ArrayList<>(Arrays.asList(filter.split(" ")));
        boolean res = true;
        for (Person p: testList) {
            boolean containsAllWords = true;
            for (String word: filterWords) {
                if (!p.getFullName().toLowerCase().contains(word)) {
                    containsAllWords = false;
                    break;
                }
            }
            if (containsAllWords && !checkableList.contains(p))
                res = false;
            else if (!containsAllWords && checkableList.contains(p))
                res = false;
        }
        return res;
    }

    /**
     * Фильтрует тестовых пользователей по фильтру lol
     */
    @Test
    void filterPersonData_Lol() {
        filter.setFilter("lol");
        filter.setPersonData(testList);
        assertTrue(checkCorrect(filter.filterPersonData(), "lol"));
    }

    /**
     * Фильтрует тестовых пользователей по фильтру lol kek
     */
    @Test
    void filterPersonData_LolKek() {
        filter.setFilter("lol kek");
        filter.setPersonData(testList);
        assertTrue(checkCorrect(filter.filterPersonData(), "lol kek"));
    }

    /**
     * Фильтрует тестовых пользователей по фильтру Box
     */
    @Test
    void filterPersonData_Box() {
        filter.setFilter("Box");
        filter.setPersonData(testList);
        assertTrue(checkCorrect(filter.filterPersonData(), "Box"));
    }
}