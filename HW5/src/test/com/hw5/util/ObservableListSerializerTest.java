package com.hw5.util;

import com.hw5.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ObservableListSerializerTest {

    private final ObservableListSerializer serializer = new ObservableListSerializer("");
    private final ObservableList<Person> testList = FXCollections.observableArrayList(
            new Person("Lol", "kek", ""),
            new Person("Lol", "kek", "Cheburek"),
            new Person("Lol1", "kek", ""),
            new Person("sdfsdfsdf", "sdfsdfsdf", "lll")
    );

    /**
     * Тестирует сериализацию в существующий файл
     */
    @Test
    void serialize_toExistingFile_True() {
        File newFile = new File("TestForSerialize/serializeTest1.bin");
        serializer.setFile(newFile);
        serializer.serialize(testList);
        assertTrue(serializer.getIsLastOperationSuccess());
    }

    /**
     * Тестирует сериализацию в несуществующий файл
     */
    @Test
    void serialize_toNotExistingFile_True() {
        File newFile = new File("TestForSerialize/serializeTest2.bin");
        if (newFile.exists())
            newFile.delete();
        serializer.setFile(newFile);
        serializer.serialize(testList);
        assertTrue(serializer.getIsLastOperationSuccess());
    }

    /**
     * Тестирует сериализацию в некорректный файл
     */
    @Test
    void serialize_incorrectFile_False() {
        File newFile = new File("");
        if (newFile.exists())
            newFile.delete();
        serializer.setFile(newFile);
        serializer.serialize(testList);
        assertFalse(serializer.getIsLastOperationSuccess());
    }

    /**
     * Тестирует десериализацию из существующего файла
     */
    @Test
    void deserialize_fromExistingFile_TrueAndListsEquals() {
        File newFile = new File("TestForSerialize/serializeTest1.bin");
        serializer.setFile(newFile);
        ObservableList<Person> list = serializer.deserialize();
        assertTrue(serializer.getIsLastOperationSuccess());
        assertEquals(list, testList);
    }

    /**
     * Тестирует десериализацию из не существующего файла
     */
    @Test
    void deserialize_fromNotExistingFile_TrueAndListsEquals() {
        File newFile = new File("TestForSerialize/serializeTest3.bin");
        serializer.setFile(newFile);
        ObservableList<Person> list = serializer.deserialize();
        assertFalse(serializer.getIsLastOperationSuccess());
        assertEquals(list.size(), 0);
    }

    /**
     * Тестирует десериализацию из файла с мусором
     */
    @Test
    void deserialize_fromFileWithRubbish_FalseAndListEmpty() {
        File newFile = new File("TestForSerialize/deserializeTest1.bin");
        serializer.setFile(newFile);
        ObservableList<Person> list = serializer.deserialize();
        assertFalse(serializer.getIsLastOperationSuccess());
        assertEquals(list.size(), 0);
    }
}