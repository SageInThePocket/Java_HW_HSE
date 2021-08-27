package com.hw5.util;

import com.hw5.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

/**
 * Сериализцет и десериализцет ObservableList в бинарный
 * файл
 */
public class ObservableListSerializer {

    private File file; //Файл в который сериализуем/из которого дисериализуем
    private boolean isLastOperationSuccess = true; //Выполнение прошлой операции прошло успешно

    /**
     * Конструктор по файлу
     * @param file файл для сериализации/десериализации
     */
    public ObservableListSerializer(File file) {
        this.file = file;
    }

    /**
     * Обращается в файл по переданному пути
     * @param path путь к файлу для сериализации/десериализации
     */
    public ObservableListSerializer(String path) {
        file = new File(path);
    }

    /**
     * @return успешно ли выполнилась предыдущая операция
     */
    public boolean getIsLastOperationSuccess() {
        return isLastOperationSuccess;
    }

    /**
     * Сериализует переданный лист с контактами
     * @param list сериализуемый лист с контактами
     */
    public void serialize(ObservableList<Person> list) {
        try {
            if (!file.exists())
                file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file.getAbsolutePath());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(new ArrayList(list));
            objectOutputStream.close();
            isLastOperationSuccess = true;
        } catch (IOException ex) {
            isLastOperationSuccess = false;
        }
    }

    /**
     * Десериализует информацию из установленного файла
     * @return полученный лист с контактами
     */
    public ObservableList<Person> deserialize() {
        if (file.exists()) {
            try {
                ObservableList<Person> list;
                FileInputStream inputStream = new FileInputStream(file.getAbsolutePath());
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                list = FXCollections.observableArrayList((ArrayList<Person>)objectInputStream.readObject());
                objectInputStream.close();
                isLastOperationSuccess = true;
                return list;
            }  catch (IOException | ClassNotFoundException ex) {
                isLastOperationSuccess = false;
                return FXCollections.observableArrayList();
            }
        }
        isLastOperationSuccess = true;
        return FXCollections.observableArrayList();
    }

    public void setFile(File file) {
        this.file = file;
    }
}
