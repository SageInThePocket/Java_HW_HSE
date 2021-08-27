package com.hw5;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.hw5.db.DataBaseManager;
import com.hw5.model.Person;
import com.hw5.util.FieldValidator;
import com.hw5.util.ObservableListSerializer;
import com.hw5.view.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApplication extends Application {

    public final DataBaseManager dbManager = new DataBaseManager( //Управляющий базой данных
            "org.apache.derby.jdbc.EmbeddedDriver",
            "TelephoneBookDB",
            "CONTACTS"
    );
    private Stage primaryStage; //Главная сцена
    private ObservableList<Person> personData = FXCollections.observableArrayList(); //Контакты
    private final ObservableListSerializer serializer =
            new ObservableListSerializer("saves/save.bin"); //Сериализатор

    /**
     * Подключается к базе данных
     */
    private void connectDB() {
        if (dbManager.connectDB())
            System.out.printf("DBManager:\tConnected to database %s was successfully.\n", dbManager.getDbName());
        else
            System.out.printf(
                    "DBManager:\tAn attempt to connect to database %s caused an SQL exception:\n%s\n",
                    dbManager.getDbName(),
                    dbManager.getLastException().getMessage()
            );
    }

    /**
     * Устанаваливает в personData контакты из базы данных
     */
    public void setAllPersonFromDB() {
        ArrayList<Person> persons = dbManager.getAllPersonsFromDB();
        if (persons != null) {
            System.out.printf("DBManager:\tLoaded all contacts from table CONTACTS in database %s was successfully.\n",
                    dbManager.getDbName());
            personData.clear();
            personData.addAll(persons);
        } else
            System.out.printf(
                    "DBManager:\tAn attempt to load all contacts from table CONTACTS in database %s " +
                            "caused an SQL exception:\n%s\n",
                    dbManager.getDbName(),
                    dbManager.getLastException().getMessage()
            );
    }

    @Override
    public void start(Stage primaryStage) {
        connectDB();
        setAllPersonFromDB();

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Телефонная книга");
        showMainWindow();
    }

    /**
     * Отображает главное окно приложения
     */
    public void showMainWindow() {
        try {
            //Загружаем fxml-файл и создаём новую сцену
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("view/main_layout.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setMinHeight(400);
            primaryStage.setMinWidth(600);

            //Получаем информацию о контактах из файла
//            personData = serializer.deserialize();
//            if (!serializer.getIsLastOperationSuccess())
//                AlertManager.showInputFileAlert(primaryStage);

            //Передаём в контроллер нужную информацию
            MainLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Отображение окна для редактирования/добавления контакта
     *
     * @param person редактируемый/добавляемый пользователь
     * @return True - Если была нажата кнопка Сохронить, False - в ином случае
     */
    public boolean showPersonEditDialog(Person person) {
        try {
            //Загружаем fxml-файл и создаём новую сцену
            //для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("view/edit_layout.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            //Создаём диалоговое окно Stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setResizable(false);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //Передаём в контроллер нужную информацию
            EditLayoutController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);
            controller.setPersonData(personData);

            //Чистим все сообщения об ошибках от прошлых использований
            FieldValidator.clearErrorList();

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();
            return controller.getIsSaveClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Отображение окна для импорта данных
     *
     * @return импортируеммые контакты
     */
    public File showImportDialog() {
        try {
            //Загружаем fxml-файл и создаём новую сцену
            //для всплывающего диалогового окна
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("view/import_layout.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            //Создаём диалоговое окно Stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Импорт");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setResizable(false);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //Передаём в контроллер нужную информацию
            ImportLayoutController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            //Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

            return controller.getSelectedFile();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Отображает окно для экспорта данных
     */
    public File showExportDialog() {
        try {
            //Загружаем fxml-файл и создаём новую сцену
            //для всплывающего диалогового окна
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("view/export_layout.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            //Создаём диалоговое окно Stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Экспорт");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setResizable(false);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //Передаём в контроллер нужную информацию
            ExportLayoutController controller = loader.getController();
            controller.setContacts(personData);
            controller.setDialogStage(dialogStage);

            //Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();
            return controller.getCreatedFile();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Отображает окно для решения конфликта при имопрте файла
     *
     * @param fromBook контакт, существующий в телефонной книге
     * @param fromFile контакт из импортиуемого файла
     * @return True - нажата кнопка Заменить, False - в ином случае
     */
    public boolean showConflictDialog(Person fromBook, Person fromFile) {
        try {
            //Загружаем fxml-файл и создаём новую сцену
            //для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("view/conflict_layout.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            //Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Импорт существующего контакта");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setResizable(false);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //Передаём в контроллер нужную информацию
            ConflictLayoutController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPersonFromBook(fromBook);
            controller.setPersonFromFile(fromFile);

            //Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();
            return controller.getIsReplaceBtnClick();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @return хранящиеся контакты
     */
    public ObservableList<Person> getPersonData() {
        return personData;
    }

    /**
     * @return галвная сцена
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void closeDB() {
        if (dbManager.closeDB()) {
            System.out.println("DBManager:\tClosed connection");
            if (!dbManager.isNormallyClosed())
                System.out.printf(
                        "DBManager:\tDatabase %s did not shut down normally.\n",
                        dbManager.getDbName()
                );
            else
                System.out.printf(
                        "DBManager:\tDatabase %s shut down normally.\n",
                        dbManager.getDbName()
                );
        } else
            System.out.printf(
                    "DBManager:\tAttempt to close database %s caused an exception:\n%s\n",
                    dbManager.getDbName(),
                    dbManager.getLastException().getMessage()
            );
    }

    @Override
    public void stop() throws Exception {
        closeDB();

//        File savesDir = new File("saves");
//        if (!savesDir.exists())
//            savesDir.mkdir();
//        serializer.serialize(personData);
//        if (!serializer.getIsLastOperationSuccess())
//            AlertManager.showOutputFileAlert(primaryStage);

        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}