package com.hw5.view;

import com.hw5.MainApplication;
import com.hw5.db.CollisionListener;
import com.hw5.model.Person;
import com.hw5.util.AlertManager;
import com.hw5.util.FullNameFilter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainLayoutController implements CollisionListener {
    @FXML
    private MenuItem deleteMenuBtn; //Кнопка в меню для удаления
    @FXML
    private MenuItem editMenuBtn; //Кнопка в меню для изменений

    @FXML
    private TextField filterField; //Поле для фильтрации
    @FXML
    private Button deleteBtn; //Кнопка для удаления
    @FXML
    private Button editBtn; //Кнопка для изменений

    @FXML
    private TableView<Person> personTableView; //Таблица с данными о контактах
    @FXML
    private TableColumn<Person, String> secondNameColumn; //Столбец с фамилиями
    @FXML
    private TableColumn<Person, String> firstNameColumn; //Столбец с именами
    @FXML
    private TableColumn<Person, String> patronymicColumn; //Столбец с отчествами
    @FXML
    private TableColumn<Person, String> telephoneNumberColumn; //Столбец с телефонами
    @FXML
    private TableColumn<Person, String> addressColumn; //Столбец с адресами
    @FXML
    private TableColumn<Person, String> dateOfBirthColumn; //Столбец с датами рождения
    @FXML
    private TableColumn<Person, String> commentColumn; //Столбец с комментариями

    private MainApplication mainApp;
    private FullNameFilter filter = new FullNameFilter(); //Фильтратор

    /**
     * Устанавливает значение disable у кнопок для редактирования
     * и удаления пользователей
     * @param val новое значение
     */
    private void setDisableOfButtons(boolean val) {
        deleteBtn.setDisable(val);
        editBtn.setDisable(val);
        editMenuBtn.setDisable(val);
        deleteMenuBtn.setDisable(val);
    }

    /**
     * Объединяет домашний телефон и мобильный телефон в одну строку
     * @param cellData ячейка хранящая информацию о пользователе
     * @return совмещенные мобильный и домашний телефоны
     */
    private String telephoneNumbersToStr(TableColumn.CellDataFeatures<Person, String> cellData) {
        String telNum = cellData.getValue().getTelephoneNumber();
        String homeTelNum = cellData.getValue().getHomeTelephoneNumber();
        if (!telNum.isEmpty() && !homeTelNum.isEmpty())
            return String.format("%s / %s", telNum, homeTelNum);
        if (telNum.isEmpty())
            return homeTelNum;
        return telNum;
    }

    /**
     * Инициализатор
     */
    @FXML
    private void initialize() {
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        secondNameColumn.setCellValueFactory(cellData -> cellData.getValue().getSecondNameProperty());
        patronymicColumn.setCellValueFactory(cellData -> cellData.getValue().getPatronymicProperty());
        telephoneNumberColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(telephoneNumbersToStr(cellData)));
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().getAddressProperty());
        dateOfBirthColumn.setCellValueFactory(cellData -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                String dataStr = formatter.format(cellData.getValue().getDateOfBirth());
                return new SimpleStringProperty(dataStr);
        });
        commentColumn.setCellValueFactory(cellData -> cellData.getValue().getCommentProperty());
    }

    /**
     * Устанавливает mainApplication
     */
    public void setMainApp(MainApplication mainApp) {
        this.mainApp = mainApp;
        personTableView.setItems(mainApp.getPersonData());
        personTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) ->
                    setDisableOfButtons(false)
                );
    }

    /**
     * Удаляет контакт из базы данных
     * @param person удаляемый контакт
     */
    private void deletePersonFromDB(Person person) {
        if (!mainApp.dbManager.deletePerson(person.getId()))
            System.out.printf(
                    "DBManager:\tAn attempt to delete contact in table caused an SQL exception:\n%s\n%s",
                    mainApp.dbManager.getLastException().getMessage(),
                    person
            );
        else
            System.out.print("DBManager:\tPerson was deleted successfully.\n");
    }

    /**
     * Обрабатывает удаление контакта
     */
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Person removedPerson = personTableView.getItems().remove(selectedIndex);
            mainApp.getPersonData().remove(removedPerson);
            if (mainApp.getPersonData().size() == 0)
                setDisableOfButtons(true);
            deletePersonFromDB(removedPerson);
        } else
            AlertManager.showNoPersonSelectedAlert(mainApp.getPrimaryStage());
    }

    /**
     * Редактирует контакт в базе данных
     * @param person изменяемый контакт
     */
    private void editPersonInDB(Person person) {
        if (!mainApp.dbManager.editPerson(person))
            System.out.printf(
                    "DBManager:\tAn attempt to edit contact in table caused an SQL exception:\n%s\n%s",
                    mainApp.dbManager.getLastException().getMessage(),
                    person
            );
        else
            System.out.print("DBManager:\tPerson was edited successfully.\n");
    }

    /**
     * Обрабатывает редактирование контакта
     */
    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTableView.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            mainApp.showPersonEditDialog(selectedPerson);
            personTableView.refresh();
            editPersonInDB(selectedPerson);
        } else
            AlertManager.showNoPersonSelectedAlert(mainApp.getPrimaryStage());
    }

    /**
     * Добавляет контакт в базу данных
     * @param person добавляемый контакт
     */
    private void addPersonToDB(Person person) {
        if (!mainApp.dbManager.addPerson(person))
            System.out.printf(
                    "DBManager:\tAn attempt to add new contact to table caused an SQL exception:\n%s\n%s",
                    mainApp.dbManager.getLastException().getMessage(),
                    person
            );
        else
            System.out.printf("DBManager:\tNew person was added successfully:\n%s\n", person);
    }

    /**
     * Обрабатывает добавление контакта
     */
    @FXML
    private void handleAddPerson() {
        Person newPerson = new Person();
        if (mainApp.showPersonEditDialog(newPerson)) {
            mainApp.getPersonData().add(newPerson);
            handleFindPerson();
            personTableView.refresh();
            addPersonToDB(newPerson);
        }
    }

    /**
     * Обрабатывает поиск контакта
     */
    @FXML
    private void handleFindPerson() {
        //Текущее значение фильтра
        String currFilter = filterField.getText().toLowerCase().trim();
        if (!currFilter.equals("")) {
            //Фильтрация с БД
            ArrayList<Person> filteredData = mainApp.dbManager.search(currFilter);
            if (filteredData != null) {
                System.out.printf(
                        "DBManager:\tFiltered data with string \"%s\" in database %s was successfully.\n",
                        currFilter,
                        mainApp.dbManager.getDbName()
                );
                ObservableList<Person> filteredDataForTable = FXCollections.observableArrayList(filteredData);
                personTableView.setItems(filteredDataForTable);
            } else
                System.out.printf(
                        "DBManager:\tAn attempt to filtered data with string \"%s\" in database %s caused an SQL exception:\n%s\n",
                        currFilter,
                        mainApp.dbManager.getDbName(),
                        mainApp.dbManager.getLastException()
                );

            //Для фильтрации контактов без БД
//            filter.setFilter(currFilter);
//            filter.setPersonData(mainApp.getPersonData());
//            personTableView.setItems(filter.filterPersonData());
        } else
            personTableView.setItems(mainApp.getPersonData());
    }

    /**
     * Обработчик закрытия окна
     */
    @FXML
    private void handleCloseWindow() {
        mainApp.getPrimaryStage().close();
    }

    /**
     * Обработчик импорта пользователя
     */
    @FXML
    private void handleImportPerson() {
        mainApp.dbManager.subscribeToCollisionEvent(this);
        File selectedFile = mainApp.showImportDialog();
        if (selectedFile != null) {
            if (!mainApp.dbManager.importData(selectedFile))
                AlertManager.showInputFileAlert(mainApp.getPrimaryStage());
            else {
                mainApp.setAllPersonFromDB();
                personTableView.refresh();
            }
        }
        mainApp.dbManager.unsubscribeToCollisionEvent(this);
    }

    /**
     * Обработчик нажатия на кнопку Справка
     */
    @FXML
    private void handleReferenceBtn() {
        AlertManager.showInfoAboutAuthorAlert(mainApp.getPrimaryStage());
    }

    /**
     * Обработчик экспорта данных
     */
    @FXML
    private void handleExportData() {
        File selectedFile = mainApp.showExportDialog();
        if (selectedFile != null) {
            if (!mainApp.dbManager.exportData(selectedFile))
                AlertManager.showOutputFileAlert(mainApp.getPrimaryStage());
        }
    }

    @Override
    public boolean collisionHandling(Person oldPerson, Person newPerson) {
        return mainApp.showConflictDialog(oldPerson, newPerson);
    }
}
