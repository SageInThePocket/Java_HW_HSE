package com.hw5.db;

import com.hw5.model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DataBaseManagerTest {

    DataBaseManager dbManager = new DataBaseManager(
            "org.apache.derby.jdbc.EmbeddedDriver",
            "TelephoneBookDB",
            "TEST_CONTACTS"
    );

    @Test
    void connectDB_ConnectDataBaseWhenManagerWasNotConnected_True() {
        DataBaseManager manager = new DataBaseManager(
                "org.apache.derby.jdbc.EmbeddedDriver",
                "TelephoneBookDB",
                "TEST_CONTACTS"
        );
        assertTrue(manager.connectDB());
    }

    @Test
    void connectDB_ConnectDataBaseWhenManagerWasConnected_False() {
        DataBaseManager manager = new DataBaseManager(
                "org.apache.derby.jdbc.EmbeddedDriver",
                "TelephoneBookDB",
                "TEST_CONTACTS"
        );
        manager.connectDB();
        assertFalse(manager.connectDB());
    }

    @Test
    void getAllPersonsFromDB_FromEmptyTable_EmptyArrayList() {
        dbManager.connectDB();
        dbManager.truncateTable();
        assertTrue(dbManager.getAllPersonsFromDB().isEmpty());
    }

    @Test
    void getAllPersonsFromDB_FromEmptyTable_NotEmptyArrayList() {
        dbManager.connectDB();
        dbManager.truncateTable();
        ArrayList<Person> people = new ArrayList<>();
        people.add(new Person("Кек1", "Лол1", "Чебурек1"));
        people.add(new Person("Кек2", "Лол2", "Чебурек2"));
        people.add(new Person("Кек3", "Лол3", "Чебурек3"));
        for (Person p : people) {
            p.setTelephoneNumber("88005553535");
            dbManager.addPerson(p);
        }
        ArrayList<Person> peopleFromDB = dbManager.getAllPersonsFromDB();
        boolean equalLists = true;
        for (Person p : people) {
            boolean hasCopy = false;
            for (Person pdb : peopleFromDB)
                if (p.isCopy(pdb))
                    hasCopy = true;
            if (!hasCopy)
                equalLists = false;
        }
        assertTrue(equalLists);
    }

    @Test
    void addPerson_CorrectPerson_SuccessfulAdd() {
        dbManager.connectDB();
        dbManager.truncateTable();
        Person person = new Person("Кек1", "Лол1", "Чебурек1");
        person.setTelephoneNumber("88005553535");
        assertTrue(dbManager.addPerson(person));
        ArrayList<Person> personFromDB = dbManager.getAllPersonsFromDB();
        assertTrue(personFromDB.size() == 1 && person.isCopy(personFromDB.get(0)));
    }

    @Test
    void addPerson_PersonWithoutName_FailedAddAndEmptyArrayList() {
        dbManager.connectDB();
        dbManager.truncateTable();
        Person person = new Person("", "Лол1", "Чебурек1");
        person.setTelephoneNumber("88005553535");
        assertFalse(dbManager.addPerson(person));
        ArrayList<Person> personFromDB = dbManager.getAllPersonsFromDB();
        assertTrue(personFromDB.isEmpty());
    }

    @Test
    void addPerson_PersonWithoutSurname_FailedAddAndEmptyArrayList() {
        dbManager.connectDB();
        dbManager.truncateTable();
        Person person = new Person("Kek", "", "Чебурек1");
        person.setTelephoneNumber("88005553535");
        assertFalse(dbManager.addPerson(person));
        ArrayList<Person> personFromDB = dbManager.getAllPersonsFromDB();
        assertTrue(personFromDB.isEmpty());
    }

    @Test
    void addPerson_EmptyPerson_FailedAddAndEmptyArrayList() {
        dbManager.connectDB();
        dbManager.truncateTable();
        Person person = new Person();
        person.setTelephoneNumber("88005553535");
        assertFalse(dbManager.addPerson(person));
        ArrayList<Person> personFromDB = dbManager.getAllPersonsFromDB();
        assertTrue(personFromDB.isEmpty());
    }

    @Test
    void addPerson_PersonWithoutTelephones_FailedAddAndEmptyArrayList() {
        dbManager.connectDB();
        dbManager.truncateTable();
        Person person = new Person("Кек1", "Лол1", "Чебурек1");
        assertFalse(dbManager.addPerson(person));
        ArrayList<Person> personFromDB = dbManager.getAllPersonsFromDB();
        assertTrue(personFromDB.isEmpty());
    }

    @Test
    void deletePerson_PersonWhoIsInDatabase_SuccessfulDelete() {
        dbManager.connectDB();
        dbManager.truncateTable();
        Person person = new Person("Кек1", "Лол1", "Чебурек1");
        person.setTelephoneNumber("88005553535");
        dbManager.addPerson(person);
        assertTrue(dbManager.deletePerson(person.getId()));
        assertTrue(dbManager.getAllPersonsFromDB().isEmpty());
    }

    @Test
    void deletePerson_PersonWhoIsNotInDatabase_UselessSuccessfulDelete() {
        dbManager.connectDB();
        dbManager.truncateTable();
        Person person = new Person("Кек1", "Лол1", "Чебурек1");
        person.setTelephoneNumber("88005553535");
        dbManager.addPerson(person);
        assertTrue(dbManager.deletePerson(person.getId() + 1));
        assertEquals(dbManager.getAllPersonsFromDB().size(), 1);
        assertTrue(dbManager.getAllPersonsFromDB().get(0).isCopy(person));
    }

    @Test
    void deletePerson_IncorrectId_FailedDelete() {
        dbManager.connectDB();
        dbManager.truncateTable();
        Person person = new Person("Кек1", "Лол1", "Чебурек1");
        person.setTelephoneNumber("88005553535");
        dbManager.addPerson(person);
        assertFalse(dbManager.deletePerson(-1));
        assertEquals(dbManager.getAllPersonsFromDB().size(), 1);
        assertTrue(dbManager.getAllPersonsFromDB().get(0).isCopy(person));
    }

    @Test
    void editPerson_CorrectEditPerson_SuccessfulEdit() {
        dbManager.connectDB();
        dbManager.truncateTable();
        Person person = new Person("Кек1", "Лол1", "Чебурек1");
        person.setTelephoneNumber("88005553535");
        dbManager.addPerson(person);

        person.setTelephoneNumber("89999999999");
        person.setComment("Ljkldfjlgkfdjg");
        person.setFirstName("ANTOSHKA");
        person.setSecondName("KARTOSHKA");
        person.setPatronymic("");
        person.setHomeTelephoneNumber("84955944994");

        assertTrue(dbManager.editPerson(person));
        ArrayList<Person> personFromDB = dbManager.getAllPersonsFromDB();
        assertTrue(personFromDB.size() == 1 && person.isCopy(personFromDB.get(0)));
    }

    @Test
    void editPerson_EditNumbersToBlank_FailedEditAndPersonWontChange() {
        dbManager.connectDB();
        dbManager.truncateTable();
        Person person = new Person("Кек1", "Лол1", "Чебурек1");
        Person savePerson = new Person("Кек1", "Лол1", "Чебурек1");
        person.setTelephoneNumber("88005553535");
        savePerson.setTelephoneNumber("88005553535");
        dbManager.addPerson(person);

        person.setTelephoneNumber("");
        person.setComment("Ljkldfjlgkfdjg");
        person.setFirstName("ANTOSHKA");
        person.setSecondName("KARTOSHKA");
        person.setPatronymic("");
        person.setHomeTelephoneNumber("");

        assertFalse(dbManager.editPerson(person));
        ArrayList<Person> personFromDB = dbManager.getAllPersonsFromDB();
        assertTrue(personFromDB.size() == 1 && savePerson.isCopy(personFromDB.get(0)));
    }

    @Test
    void editPerson_EditNameToBlank_FailedEditAndPersonWontChange() {
        dbManager.connectDB();
        dbManager.truncateTable();
        Person person = new Person("Кек1", "Лол1", "Чебурек1");
        Person savePerson = new Person("Кек1", "Лол1", "Чебурек1");
        person.setTelephoneNumber("88005553535");
        savePerson.setTelephoneNumber("88005553535");
        dbManager.addPerson(person);

        person.setTelephoneNumber("");
        person.setComment("Ljkldfjlgkfdjg");
        person.setFirstName("");
        person.setSecondName("KARTOSHKA");
        person.setPatronymic("");
        person.setHomeTelephoneNumber("");

        assertFalse(dbManager.editPerson(person));
        ArrayList<Person> personFromDB = dbManager.getAllPersonsFromDB();
        assertTrue(personFromDB.size() == 1 && savePerson.isCopy(personFromDB.get(0)));
    }

    @Test
    void search_KekLolText_TwoPersons() {
        dbManager.connectDB();
        dbManager.truncateTable();
        ArrayList<Person> people = new ArrayList<>();
        people.add(new Person("Кек1", "Лол1", "Чебурек1"));
        people.add(new Person("Кек2", "Лол2", "Чебурек2"));
        people.add(new Person("Ккк1", "Лол3", "Чебурек3"));
        for (Person p : people) {
            p.setTelephoneNumber("88005553535");
            dbManager.addPerson(p);
        }
        ArrayList<Person> searchPeople = dbManager.search("Кек лол");
        assertTrue(searchPeople.contains(people.get(0)));
        assertTrue(searchPeople.contains(people.get(1)));
        assertEquals(searchPeople.size(), 2);
    }

    @Test
    void search_Cheburek_AllPersons() {
        dbManager.connectDB();
        dbManager.truncateTable();
        ArrayList<Person> people = new ArrayList<>();
        people.add(new Person("Кек1", "Лол1", "Чебурек1"));
        people.add(new Person("Кек2", "Лол2", "Чебурек2"));
        people.add(new Person("Ккк1", "Лол3", "Чебурек3"));
        for (Person p : people) {
            p.setTelephoneNumber("88005553535");
            dbManager.addPerson(p);
        }
        ArrayList<Person> searchPeople = dbManager.search("ЧеБуРеК");
        assertTrue(searchPeople.contains(people.get(0)));
        assertTrue(searchPeople.contains(people.get(1)));
        assertTrue(searchPeople.contains(people.get(2)));
        assertEquals(searchPeople.size(), 3);
    }

    @Test
    void search_Elephant_Empty() {
        dbManager.connectDB();
        dbManager.truncateTable();
        ArrayList<Person> people = new ArrayList<>();
        people.add(new Person("Кек1", "Лол1", "Чебурек1"));
        people.add(new Person("Кек2", "Лол2", "Чебурек2"));
        people.add(new Person("Ккк1", "Лол3", "Чебурек3"));
        for (Person p : people) {
            p.setTelephoneNumber("88005553535");
            dbManager.addPerson(p);
        }
        ArrayList<Person> searchPeople = dbManager.search("Слон");
        assertTrue(searchPeople.isEmpty());
    }
}