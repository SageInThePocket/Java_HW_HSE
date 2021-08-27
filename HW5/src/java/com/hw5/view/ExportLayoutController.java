package com.hw5.view;

import com.hw5.model.Person;
import com.hw5.util.AlertManager;
import com.hw5.util.ObservableListSerializer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Контроллер окна для экспорта данных
 */
public class ExportLayoutController {

    @FXML
    private Button okBtn; //Кнопка Ок
    @FXML
    private TextField fileSetField; //Поле для ввода пути

    private ObservableList<Person> contacts; //Имеющиеся контакты
    private final DirectoryChooser directoryChooser = new DirectoryChooser(); //Для выобра папки
    private File selectedDir = null; //Выбронная директория
    private File createdFile = null; //Созданный файл
    private Stage dialogStage; //Родительская сцена

    /**
     * Устанавливает список контактов
     * @param contacts список контактов
     */
    public void setContacts(ObservableList<Person> contacts) {
        this.contacts = contacts;
    }

    /**
     * Устанавливает диалоговое окно
     * @param stage сцена
     */
    public void setDialogStage(Stage stage) {
        dialogStage = stage;
    }

    /**
     * Обработчик клика по текстовому полю
     */
    @FXML
    private void handleFileSetFieldClick() {
        directoryChooser.setTitle("Directory chooser");
        selectedDir = directoryChooser.showDialog(dialogStage);
        if (selectedDir != null)
            fileSetField.setText(selectedDir.getAbsolutePath());
        okBtn.requestFocus();
    }

    /**
     * Обработчик нажатия на кнопку Ok
     */
    @FXML
    private void handleOkBtn() {
        if (selectedDir != null) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm-ss");
            String dataStr = formatter.format(now);
            String fileName = String.format("save-%s.bin", dataStr);
            createdFile = new File(selectedDir, fileName);
        }
        dialogStage.close();
    }

    /**
     * Обработчик нажатия на кнопку Отмена
     */
    @FXML
    private void cancelBtnHandle() {
        dialogStage.close();
    }

    public File getCreatedFile() {
        return createdFile;
    }
}
