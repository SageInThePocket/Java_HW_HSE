package com.hw5.db;

import com.hw5.model.Person;

/**
 * Обработчик события возникновния коллизии при импорте данных в таблицу базы данных
 */
public interface CollisionListener {
    /**
     * Обрабатывает коллизию
     * @param oldPerson старое значение контакта
     * @param newPerson новое значение контакта
     * @return нужно ли заменять старый контакт на новый или нет
     */
    boolean collisionHandling(Person oldPerson, Person newPerson);
}
