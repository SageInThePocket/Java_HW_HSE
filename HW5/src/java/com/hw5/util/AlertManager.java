package com.hw5.util;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Хранит в себе нужные мне методы для создания alert-ов
 */
public class AlertManager {

    public static void showInputFileAlert(Stage dialogStage) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(dialogStage);
        alert.setTitle("Ошибка при чтении файла");
        alert.setHeaderText("Ошибка при чтении файла");
        alert.setContentText("Не удалось прочитать информацию из файла с сохраненными данными.");
        alert.showAndWait();
    }

    public static void showOutputFileAlert(Stage dialogStage) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(dialogStage);
        alert.setTitle("Ошибка при записи в файл");
        alert.setHeaderText("Ошибка при записи в файл");
        alert.setContentText("Не удалось записать данные в файл");
        alert.showAndWait();
    }

    public static void showInfoAboutAuthorAlert(Stage dialogStage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(dialogStage);
        alert.setTitle("Информация о создетеле");
        alert.setHeaderText("Назмутдинов Роман");
        alert.setContentText("Студент группы БПИ-194");
        alert.showAndWait();
    }

    public static void showNoPersonSelectedAlert(Stage dialogStage) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(dialogStage);
        alert.setTitle("Контакт не выбран");
        alert.setHeaderText("Ни один контакт не выбран");
        alert.setContentText("Пожалуйста выберите контакт");
        alert.showAndWait();
    }
}
