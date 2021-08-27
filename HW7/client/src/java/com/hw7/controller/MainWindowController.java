package com.hw7.controller;

import com.hw7.MainApp;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import com.hw7.model.FileModel;

import java.io.File;
import java.text.SimpleDateFormat;


public class MainWindowController {
    @FXML
    private TableColumn<FileModel, Number> idColumn;
    @FXML
    private TableColumn<FileModel, String> nameColumn;
    @FXML
    private TableColumn<FileModel, String> dateColumn;
    @FXML
    private TableColumn<FileModel, String> sizeColumn;
    @FXML
    private TableView<FileModel> filesTableView;

    private MainApp mainApp;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(mainApp.getData().indexOf(cellData.getValue()) + 1));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        dateColumn.setCellValueFactory(cellData -> {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
            return new SimpleStringProperty(format.format(cellData.getValue().getEditDate()));
        });
        sizeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSizeWithUnits()));
        filesTableView.setRowFactory( tv -> {
            TableRow<FileModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    File dir = mainApp.showConfirmationWindow(row.getItem());
                    if (dir != null)
                        mainApp.showDownloadWindow(row.getItem(), dir.getAbsolutePath());
                }
            });
            return row;
        });
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        filesTableView.setItems(mainApp.getData());
    }
}
