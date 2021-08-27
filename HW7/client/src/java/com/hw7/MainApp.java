package com.hw7;

import com.hw7.controller.ConfirmController;
import com.hw7.controller.DownloadController;
import com.hw7.controller.MainWindowController;
import com.hw7.network.DownloadFileListener;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.hw7.model.FileModel;
import com.hw7.network.Server;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MainApp extends Application {
    private static Server server;
    private Stage primaryStage;
    private ObservableList<FileModel> data = FXCollections.observableArrayList();

    public ObservableList<FileModel> getData() {
        return data;
    }

    @Override
    public void start(Stage primaryStage) {
        if (server != null) {
            server.getData();
            data.addAll(server.getFileModels());

            this.primaryStage = primaryStage;
            this.primaryStage.setTitle("Torrent");
            showMainWindow();
        } else {
            System.out.print("Подключение с сервером не установлено...");
            System.exit(1);
        }
    }

    public void showMainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("layout/main_window_layout.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setMinHeight(400);
            primaryStage.setMinWidth(600);

            MainWindowController mainWindow = loader.getController();
            mainWindow.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File showConfirmationWindow(FileModel file) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("layout/confirm_layout.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(file.getName() + "." + file.getType());
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setResizable(false);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ConfirmController controller = loader.getController();
            controller.setSelectedFile(file);
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();
            return controller.getSelectedDir();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void showDownloadWindow(FileModel file, String path) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("layout/download_layout.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(file.getName() + "." + file.getType());
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setResizable(false);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            DownloadController controller = loader.getController();
            controller.setDirPath(path);
            controller.setDownloadedFile(file);
            controller.setDialogStage(dialogStage);

            dialogStage.show();
            server.getFile(file.getFullName(), path);
            while (server.fileIsDownloading()) {
                double val = server.getDownloadedBytes() * 1.0 / file.getSizeBytes();
                controller.setVal(val);
                TimeUnit.SECONDS.sleep(1);
            }
            dialogStage.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String host = in.nextLine();
        int port = Integer.parseInt(in.next());
        try {
            server = new Server(host, port);
            System.out.println("Подкючение к серверу прошло успешно.");
            launch();
        } catch (IOException e) {
            System.out.println("Не удалось подключиться к серверу.");
            System.exit(1);
        }
    }
}