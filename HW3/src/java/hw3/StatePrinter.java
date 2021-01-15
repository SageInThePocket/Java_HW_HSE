package hw3;

public class StatePrinter extends Thread {
    private final Timer timer; // Таймер вывода
    private final Object object; // Объект состояние которого будет выводиться

    public StatePrinter(Timer timer, Object object) {
        this.timer = timer;
        this.object = object;
    }

    /**
     * Выводит информацию об объекте object каждый раз, когда таймер
     * вызывает notifyAll у notifyLock.
     */
    @Override
    public void run() {
        try {
            synchronized (timer.getStartLock()) {
                while (!timer.getActiveFlag())
                    timer.getStartLock().wait();
            }
            while (!isInterrupted() && timer.getActiveFlag()) {
                synchronized (timer.getNotifyLock()) {
                    while (!timer.getNotifyFlag())
                        timer.getNotifyLock().wait();
                }
                System.out.printf("\n%s:\n%s\n", getName(),object.toString());
                timer.setNotifyFlagToFalse();
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
