package com.hw7.controller;

import com.hw7.model.FileModel;
import com.hw7.network.DownloadFileListener;
import com.hw7.network.Server;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class DownloadController {
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label fileNameLabel;
    @FXML
    private Label pathLabel;

    private FileModel downloadedFile;
    private String dirPath;
    private Stage dialogStage;

    public void setDownloadedFile(FileModel file) {
        downloadedFile = file;
        fileNameLabel.setText(String.format("Скачивание файла %s.%s", downloadedFile.getName(), downloadedFile.getType()));
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
        fileNameLabel.setText("В папку: " + dirPath);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void startDownload(Server server) {
        server.getFile(downloadedFile.getFullName(), dirPath);
        while (server.fileIsDownloading()) {
            double val = server.getDownloadedBytes() * 1.0 / downloadedFile.getSizeBytes();
            progressBar.setProgress(val);
        }
        dialogStage.close();
    }

    public void setVal(double val) {
        progressBar.setProgress(val);
    }
}
