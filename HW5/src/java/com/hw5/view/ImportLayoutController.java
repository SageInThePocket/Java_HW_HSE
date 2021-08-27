package com.hw5.view;

import com.hw5.MainApplication;
import com.hw5.model.Person;
import com.hw5.util.AlertManager;
import com.hw5.util.ObservableListSerializer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Контроллер окна для выбора импортируемого файла
 */
public class ImportLayoutController {

    @FXML
    private Button okBtn; //Кнопка Ок
    @FXML
    private TextField fileSetField; //Поле для ввода пути к файлу

    private final FileChooser fileChooser = new FileChooser(); //Для выбора файла
    private File selectedFile = null; //Выбранный пользователем файл
    private Stage dialogStage; //Родительская сцена

    public File getSelectedFile() {
        return selectedFile;
    }

    /**
     * Устанавливает родительскую сцену
     * @param stage сцена
     */
    public void setDialogStage(Stage stage) {
        dialogStage = stage;
    }

    /**
     * Обработчик нажатия на поле для ввода пути к файлу
     */
    @FXML
    private void handleFileSetFieldClick() {
        fileChooser.setTitle("Import file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Bin files", "*.bin")
        );
        selectedFile = fileChooser.showOpenDialog(dialogStage);
        if (selectedFile != null)
            fileSetField.setText(selectedFile.getAbsolutePath());
        okBtn.requestFocus();
    }

    /**
     * Обработчик нажатия на кнопку Ок
     */
    @FXML
    private void handleOkBtn() {
        dialogStage.close();
    }

    /**
     * Обработчик нажатия на кнопку Отмена
     */
    @FXML
    private void handleCancelBtn() {
        dialogStage.close();
    }

}
