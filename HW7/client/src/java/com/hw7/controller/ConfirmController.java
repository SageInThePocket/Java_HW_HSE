package com.hw7.controller;

import com.hw7.model.FileModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class ConfirmController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label editDateLabel;
    @FXML
    private Label sizeLabel;

    private FileModel selectedFile = null;
    private Stage dialogStage;
    private final DirectoryChooser directoryChooser = new DirectoryChooser();
    private File selectedDir = null;

    public void setSelectedFile(FileModel fileModel) {
        selectedFile = fileModel;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        if (selectedFile != null) {
            nameLabel.setText(String.format("Имя: %s", selectedFile.getName()));
            editDateLabel.setText(String.format("Дата изменения: %s", selectedFile.getStringDate()));
            sizeLabel.setText(String.format("Размер: %s", selectedFile.getSizeWithUnits()));
        } else {
            dialogStage.close();
        }
    }

    public File getSelectedDir() {
        return selectedDir;
    }

    @FXML
    private void onDownloadButtonClick() {
        directoryChooser.setTitle("Directory chooser");
        selectedDir = directoryChooser.showDialog(dialogStage);
        if (selectedDir != null)
            dialogStage.close();
    }

    @FXML
    private void onCancelClick() {
        dialogStage.close();
    }

}
