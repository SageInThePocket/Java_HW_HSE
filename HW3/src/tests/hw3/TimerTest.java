package hw3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimerTest {
    Timer timer;

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
    }

    /**
     * Проверяем работу метода в двух ситуациях.
     * Получаем значение
     */
    @Test
    void setNotifyFlagToFalse() {
        timer.setNotifyFlagToFalse();
        assertFalse(timer.getNotifyFlag());
        timer.start();
        delay(2020);
        timer.interrupt();
        assertTrue(timer.getNotifyFlag());
        timer.setNotifyFlagToFalse();
        assertFalse(timer.getNotifyFlag());
    }

    @Test
    void getActiveFlag() {
        assertFalse(timer.getNotifyFlag());
        timer.start();
        delay(10);
        assertTrue(timer.getActiveFlag());
        timer.interrupt();
        delay(10);
        assertFalse(timer.getActiveFlag());
    }

    @Test
    void getNotifyFlag() {
        assertFalse(timer.getNotifyFlag());
        timer.start();
        delay(1990);
        assertFalse(timer.getNotifyFlag());
        delay(30);
        timer.interrupt();
        assertTrue(timer.getNotifyFlag());
    }
}