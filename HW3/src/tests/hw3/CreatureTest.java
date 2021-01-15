package hw3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.util.Currency;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CreatureTest {
    Timer timer;
    Cart cart;
    Timer newTimer;
    Cart newCart;

    /**
     * Безопасный вызов sleep
     * @param millis мс на которые поток должен уснуть
     * @return True - если задержка завершилась корректно и
     * False - в случае если во время задержки поток был прерван
     */
    boolean delay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            return false;
        }
        return true;
    }

    @BeforeEach
    void setUp() {
        timer = new Timer(25, 2);
        cart = new Cart(0, 0);
        newTimer = new Timer(25, 2);
        newCart = new Cart(0, 0);
    }

    /**
     * Проверяет все возможные случаи установки таймера:
     * Установка нового таймер;
     * Установка null в качестве таймера;
     * Установка таймера, когда таймер класса запущен;
     * Установка запущенного таймера;
     */
    @Test
    void setTimer() {
        assertTrue(Creature.setTimer(timer)); // Проверяем, что таймер корректно устанавливается
        assertEquals(timer, Creature.getTimer()); // Проверяем, что таймеры равны
        // Устанавливаем null в качестве таймера
        assertFalse(Creature.setTimer(null));
        // Устанавливаем новый таймер во время работы старого
        timer.start();
        while (!delay(10)); // Делаем задержку и ждем ее корректного заверщения (Даем таймеру время на запуск)
        assertFalse(Creature.setTimer(newTimer));
        timer.interrupt();
        // Устанавливаем запущенный таймер
        newTimer.start();
        while (!delay(10)); // Делаем задержку и ждем ее корректного заверщения (Даем таймеру время на запуск)
        assertFalse(Creature.setTimer(newTimer));
        newTimer.interrupt();
    }

    /**
     * Проверяем все возможные случаи установки телеги:
     * Установка телеги новой телеги в класс;
     * Установка null в качестве телеги;
     * Установка телеги, когда запущент таймер;
     */
    @Test
    void setCart() {
        assertTrue(Creature.setCart(cart)); // Проверяем, что телега без проблем устанавливается
        assertEquals(Creature.getCart(), cart); // Проверяем, что телеги совпадают
        // Устанавливаем null в качестве таймера
        assertFalse(Creature.setCart(null));
        // Попытаемся установить новую телегу во время работы таймера
        Creature.setTimer(timer);
        timer.start();
        while (!delay(10)); //Делаем задержку и ждем ее корректного заверщения (Даем таймеру время на запуск)
        assertFalse(Creature.setCart(newCart));
    }

    @Test
    void getTimer() {
        Creature.setTimer(timer);
        assertEquals(Creature.getTimer(), timer);
    }

    @Test
    void getCart() {
        Creature.setCart(cart);
        assertEquals(Creature.getCart(), cart);
    }

    @Test
    void testToString() {
        for (int i = 0; i < 1000; i++)
            for (int j = 0; j < 360; j++) {
                Creature creature = new Creature(j, i/100.0);
                double changeX = i/100.0 * Math.cos(j / 180.0 * Math.PI);
                double changeY = i/100.0 * Math.sin(j / 180.0 * Math.PI);
                creature.setName("test");
                assertEquals(creature.toString(), String.format("test: change x = %.2f, change y = %.2f;",
                        changeX, changeY));
            }
    }
}