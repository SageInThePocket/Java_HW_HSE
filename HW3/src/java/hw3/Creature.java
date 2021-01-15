package hw3;

import java.sql.Time;
import java.util.Random;

public class Creature extends Thread {
    private static Timer timer = new Timer(25, 2); // Таймер определающий конец толкания телеги
    private static Cart cart = new Cart(0, 0); // Телега
    final double changeX; // Изменение координаты X
    final double changeY; // Изменение координаты Y

    public Creature(int angle, double coef) {
        this.changeX = coef * Math.cos(angle / 180.0 * Math.PI);
        this.changeY = coef * Math.sin(angle / 180.0 * Math.PI);
    }

    public Creature(int angle, double coef, String name) {
        this.changeX = coef * Math.cos(angle / 180.0 * Math.PI);
        this.changeY = coef * Math.sin(angle / 180.0 * Math.PI);
        setName(name);
    }

    /**
     * Устанавливает новый таймер если это можно сделать
     * @param newTimer новый таймер
     * @return True - если новый таймер был установлен успешно и
     * False - в ином случае
     */
    public static boolean setTimer(Timer newTimer) {
        if (newTimer == null)
            return false;
        if ((timer == null || !timer.getActiveFlag()) && !newTimer.getActiveFlag()) {
            timer = newTimer;
            return true;
        }
        return false;
    }

    /**
     * Устанавливает новую телегу
     * @param newCart новая телега
     * @return проверяет можно ли установить телегу
     */
    public static boolean setCart(Cart newCart) {
        if (newCart == null)
            return false;
        if (timer == null || !timer.getActiveFlag()) {
            cart = newCart;
            return true;
        }
        return false;
    }

    /**
     * @return таймер существ
     */
    public static Timer getTimer() {
        return timer;
    }

    /**
     * @return телега существ
     */
    public static Cart getCart() {
        return cart;
    }

    /**
     * Существа начинают тянуть телегу в разные стороны
     */
    @Override
    public void run() {
        Random threadRnd = new Random();
        synchronized (timer.getStartLock()) {
            try {
                while (!timer.getActiveFlag())
                    timer.getStartLock().wait();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        try {
            while (!isInterrupted() && timer.getActiveFlag()) {
                cart.changeCoordinates(changeX, changeY);
                int restTime = threadRnd.nextInt(4000) + 1000;
                Thread.sleep(restTime);
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public String toString() {
        return String.format("%s: change x = %.2f, change y = %.2f;", getName(), changeX, changeY);
    }
}
