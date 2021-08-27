package com.hw5.db;

import com.hw5.model.Person;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

class Car {
    String color;
    int countOfWheels;
    int maxSpeed;

    public Car(String color, int countOfWheels, int maxSpeed) {
        this.color = color;
    }


}

/**
 * Класс предназначенный для работы с таблицей в базе данных
 */
public class DataBaseManager {
    private final String tableName; //Название таблицы с которой ведется работа
    private final String createString; //Запрос для создания таблицы в бд
    private final String driver; //Драйвер
    private final String dbName; //Имя базы данных
    private final String connectionUrl; //Строка для подключения к Derby
    private Connection conn;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private Exception lastException; //Последнее возникшее исключение
    private boolean isClosed = true; //Закончили ли работу с бд
    private boolean isNormallyClosed = true;
    private final ArrayList<CollisionListener> collisionListeners = new ArrayList<>(); //Слушатели возникновения коллизий

    public DataBaseManager(String driver, String dbName, String tableName) {
        if (tableName.contains(" "))
            throw new IllegalArgumentException("Название таблицы должно состоять из одного слова");

        this.driver = driver;
        this.dbName = dbName;
        this.tableName = tableName;
        connectionUrl = "jdbc:derby:" + dbName + ";create=true";
        createString =
                "CREATE TABLE " + tableName + " "
                        + "(ID INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                        + " SURNAME VARCHAR(32) NOT NULL, "
                        + " NAME VARCHAR(32) NOT NULL, "
                        + " PATRONYMIC VARCHAR(32), "
                        + " MOBILE VARCHAR(32),"
                        + " HOME_TELEPHONE VARCHAR(32),"
                        + " ADDRESS VARCHAR(255),"
                        + " BIRTHDAY DATE, "
                        + " COMMENTS VARCHAR(255))";
    }

    /**
     * Выполняет подключение к БД
     * @return True - подключение прошло успешно, False - не удалось подключиться
     */
    public boolean connectDB() {
        try {
            if (conn == null) {
                conn = DriverManager.getConnection(connectionUrl);
                statement = conn.createStatement();
                if (!checkForTableExistence())
                    statement.execute(createString);
                isClosed = false;
            } else
                return false;
        } catch (SQLException exception) {
            lastException = exception;
            return false;
        }
        return true;
    }

    /**
     * Проверяет наличие таблицы в БД
     * @return True - таблица есть, False - таблицы нет
     */
    public boolean checkForTableExistence() throws SQLException {
        try {
            Statement s = conn.createStatement();
            s.execute("update " + tableName + " set NAME = 'test', SURNAME = 'test' where 1=3");
        } catch (SQLException sqle) {
            String theError = (sqle).getSQLState();

            if (theError.equals("42X05"))   // Table does not exist
                return false;
            else if (theError.equals("42X14") || theError.equals("42821")) {
                System.out.println("WwdChk4Table: Incorrect table definition. Drop table WISH_LIST and rerun this program");
                lastException = sqle;
                throw sqle;
            } else {
                System.out.println("WwdChk4Table: Unhandled SQLException");
                lastException = sqle;
                throw sqle;
            }
        }
        return true;
    }

    /**
     * Устанавливает строковое значение в колонку таблицы с переданным индексом
     * @param paramIndex индекс колонки в которую будет записано переданное значение
     * @param val записываемое значение
     */
    private void setStringToTable(int paramIndex, String val) throws SQLException {
        if (!val.isBlank())
            preparedStatement.setString(paramIndex, val);
        else
            preparedStatement.setNull(paramIndex, Types.VARCHAR);
    }

    /**
     * Уставливает дату в колонку таблицы с переданным индексом
     * @param paramIndex индекс колонки в которую будет записано переданное значение даты
     * @param date заисываемое значение
     */
    private void setDateToTable(int paramIndex, LocalDate date) throws SQLException {
        if (date != null)
            preparedStatement.setDate(paramIndex, Date.valueOf(date));
        else
            preparedStatement.setNull(paramIndex, Types.DATE);
    }

    /**
     * Устанавливает значения полей экземпляра класса Person в таблицу
     * @param person записываемый в таблицу контакт
     */
    private void setPersonProperties(Person person) throws SQLException {
        preparedStatement.setString(1, person.getSecondName());
        preparedStatement.setString(2, person.getFirstName());
        setStringToTable(3, person.getPatronymic());
        setStringToTable(4, person.getTelephoneNumber());
        setStringToTable(5, person.getHomeTelephoneNumber());
        setStringToTable(6, person.getAddress());
        setDateToTable(7, person.getDateOfBirth());
        setStringToTable(8, person.getComment());
    }

    /**
     * Устанавливает контакту его id из таблицы
     * @param person контакту, которому устанавливается id
     */
    private void setIdToPerson(Person person) throws SQLException {
        ResultSet newPerson = statement.executeQuery(
                "select ID from " + tableName + " where" +
                        " NAME = '" + person.getFirstName() + "' and " +
                        "SURNAME = '" + person.getSecondName() + "'"
        );
        if (newPerson.next())
            person.setId(newPerson.getInt(1));
    }

    /**
     * Преобразовываем данные из ResultSet в экземпляр класса Person
     * @param resultSet ResultSet с информацией о контакте
     * @return созданный экзепляр класса Person
     */
    private Person convertToPerson(ResultSet resultSet) throws SQLException {
        Person person = new Person(
                resultSet.getString(3),
                resultSet.getString(2),
                resultSet.getString(4),
                resultSet.getString(5),
                resultSet.getString(6),
                resultSet.getString(7),
                resultSet.getDate(8),
                resultSet.getString(9)
        );
        person.setId(resultSet.getInt(1));
        return person;
    }

    /**
     * Преобразовывает все записи о контактах в таблице в ArrayList контактов
     * @return ArrayList контактов, хранимых в таблице
     */
    public ArrayList<Person> getAllPersonsFromDB() {
        ArrayList<Person> personsFromDB = new ArrayList<>();
        try {
            ResultSet personsSet = statement.executeQuery("select * from " + tableName);
            while (personsSet.next())
                personsFromDB.add(convertToPerson(personsSet));
        } catch (SQLException exception) {
            lastException = exception;
            return null;
        }
        return personsFromDB;
    }

    /**
     * Добавляет контакт в таблицу
     * @param person добавляемый контакт
     * @return True - контакт успешно добавлен в таблицу, False - не удалось добавить контакт
     */
    public boolean addPerson(Person person) {
        try {
            if (!person.withoutNameAndSurname() && person.hasAtLeastOneTelephone()) {
                preparedStatement = conn.prepareStatement("insert into " + tableName + "(SURNAME, NAME, PATRONYMIC, " +
                        "MOBILE, HOME_TELEPHONE, ADDRESS, BIRTHDAY, COMMENTS) values (?, ?, ?, ?, ?, ?, ?, ?)");
                setPersonProperties(person);
                preparedStatement.executeUpdate();
                setIdToPerson(person);
            } else
                return false;
        } catch (SQLException exception) {
            lastException = exception;
            return false;
        }
        return true;
    }

    /**
     * Отчищает всю таблицу
     * @return True - операция прошла успешно, False - не удалось отчистить таблицу
     */
    public boolean truncateTable() {
        try {
            preparedStatement = conn.prepareStatement("truncate table " + tableName);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            lastException = exception;
            return false;
        }
        return true;
    }

    /**
     * Удаляет контакт с переданным id
     * @param id id удаляемого из таблицы контакта
     * @return True - удаление конкта прошло успешно, False - не удалось удалить контакт
     */
    public boolean deletePerson(int id) {
        try {
            if (id >= 0) {
                preparedStatement = conn.prepareStatement("delete from " + tableName + " where ID = ?");
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            } else
                return false;
        } catch (SQLException exception) {
            lastException = exception;
            return false;
        }
        return true;
    }

    /**
     * Изменяет переданный контакт в таблице. Изменямый контакт находится в таблице по его id
     * @param person изменяемый контакт
     * @return True - изменение контакта прошло успешно, False - не удалось изменить контакт
     */
    public boolean editPerson(Person person) {
        try {
            if (!person.withoutNameAndSurname() && person.getId() >= 0 && person.hasAtLeastOneTelephone()) {
                preparedStatement = conn.prepareStatement("update " + tableName + " set " +
                        "SURNAME = ?, NAME = ?, PATRONYMIC = ?, MOBILE = ?, " +
                        "HOME_TELEPHONE = ?, ADDRESS = ?, BIRTHDAY = ?, COMMENTS = ? " +
                        "where id = ?");
                setPersonProperties(person);
                preparedStatement.setInt(9, person.getId());
                preparedStatement.executeUpdate();
            } else
                return false;
        } catch (SQLException exception) {
            lastException = exception;
            return false;
        }
        return true;
    }

    /**
     * Находит контакты ФИО которых содержат в себе переданную комбинацию слов
     * @param str значение фильтра по которому отбираются контакты
     * @return ArrayList содержащий в себе контакты содержащие в имени все слова фильтра.
     * Возвращает null в случае если поиск контактов не удалось произвести.
     */
    public ArrayList<Person> search(String str) {
        try {
            ArrayList<Person> result = new ArrayList<>();
            ArrayList<String> searchWords = new ArrayList<>(Arrays.asList(str.toLowerCase().split(" ")));
            for (String word : searchWords) {
                ResultSet searchSet = statement.executeQuery("select * from " + tableName + " where" +
                        " lower ( NAME ) like \'%" + word + "%\' OR" +
                        " lower ( SURNAME ) like \'%" + word + "%\' OR" +
                        " lower ( PATRONYMIC ) like \'%" + word + "%\'");

                ArrayList<Person> prevRes = result;
                result = new ArrayList<>();
                while (searchSet.next()) {
                    Person person = convertToPerson(searchSet);
                    if (searchWords.get(0).equals(word)) {
                        if (!result.contains(person))
                            result.add(person);
                    } else {
                        if (prevRes.contains(person))
                            result.add(person);
                    }
                }
            }
            return result;
        } catch (SQLException ex) {
            lastException = ex;
            return null;
        }
    }

    /**
     * Проверяет наличие коллизий с переданным контактом
     * @param person контакт с которым ищем коллизии
     */
    private void checkCollisions(Person person) throws SQLException {
        ResultSet collision = statement.executeQuery("select * from " + tableName + " " +
                "where NAME = \'" + person.getFirstName() + "\' and SURNAME = \'" + person.getSecondName() +
                "\' and PATRONYMIC = \'" + person.getPatronymic() + "\'");

        if (collision.next()) {
            boolean deleteOldPerson = true;
            Person oldPerson = convertToPerson(collision);
            for (CollisionListener l : collisionListeners)
                if (!l.collisionHandling(oldPerson, person))
                    deleteOldPerson = false;

            if (deleteOldPerson) {
                person.setId(oldPerson.getId());
                editPerson(person);
            }
        } else
            addPerson(person);
    }

    /**
     * Импортирует данные из переданного файла в таблицу.
     * @param importFile испортируемый файл
     * @return True - импорт прошел успешно, False - не удалось импортировать контакты из файла
     */
    public boolean importData(File importFile) {
        if (importFile.exists()) {
            try {
                ArrayList<Person> newContacts;
                FileInputStream inputStream = new FileInputStream(importFile.getAbsolutePath());
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                newContacts = (ArrayList) objectInputStream.readObject();
                objectInputStream.close();
                for (Person p : newContacts)
                    checkCollisions(p);
            } catch (IOException | ClassNotFoundException | SQLException ex) {
                lastException = ex;
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Экспортирует данные из таблицы в переданный файл. В случае если файла нет - создает его
     * @param exportFile файл в который экспортируются данные из таблицы
     * @return True - экспорт данных прошел успешно, False - не экспортировать данные в файл
     */
    public boolean exportData(File exportFile) {
        try {
            if (!exportFile.exists() && !exportFile.createNewFile())
                return false;
            FileOutputStream outputStream = new FileOutputStream(exportFile.getAbsolutePath());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(getAllPersonsFromDB());
            objectOutputStream.close();
        } catch (IOException exception) {
            lastException = exception;
            return false;
        }
        return true;
    }

    /**
     * Добавляет переданного слушателя в список слушаетелей события возникновения коллизии
     * @param listener добавляемый слушатель
     * @return True - слушатель успешно добавлен, False - не удалось добавить слушателя так как он уже добавлен
     */
    public boolean subscribeToCollisionEvent(CollisionListener listener) {
        if (!collisionListeners.contains(listener)) {
            collisionListeners.add(listener);
            return true;
        }
        return false;
    }

    /**
     * Удаляет слушателя из списка слушателей если он есть в этом списке
     * @param listener удаляемый слушатель
     * @return True - слушатель успешно удален, False - не удалось удалить слушателя, так как его там не было
     */
    public boolean unsubscribeToCollisionEvent(CollisionListener listener) {
        return collisionListeners.remove(listener);
    }

    /**
     * Проверят нормально ли зыкрылась база данных
     * @return True - закрытие прошло успешно, False - закрытие прошло некорректно
     */
    private boolean isNormallyClosedDB() {
        boolean gotSQLExc = true;
        if (driver.equals("org.apache.derby.jdbc.EmbeddedDriver")) {
            gotSQLExc = false;
            try {
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            } catch (SQLException se) {
                if (se.getSQLState().equals("XJ015"))
                    gotSQLExc = true;
            }
        }
        return gotSQLExc;
    }

    /**
     * Завершает работу с базой данных
     * @return
     */
    public boolean closeDB() {
        try {
            if (preparedStatement != null)
                preparedStatement.close();
            statement.close();
            conn.close();
            isClosed = true;
            isNormallyClosed = isNormallyClosedDB();
        } catch (SQLException exception) {
            lastException = exception;
            return false;
        }
        return true;
    }

    /**
     * Возвращает последнее возникшее исключение и устанавливает lastException в null
     * @return последнее возникшее исключение
     */
    public Exception getLastException() {
        Exception ex = lastException;
        lastException = null;
        return ex;
    }

    /**
     * @return название базы данных
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * @return корректно ли прошло закрытие
     */
    public boolean isNormallyClosed() {
        return isNormallyClosed;
    }
}
