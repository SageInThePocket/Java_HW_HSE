package hw3;

public class Timer extends Thread {
    private boolean activeFlag = false; // Флаг активного состояния таймера
    private boolean notifyFlag = false; // Флаг сработаного notify
    private int countOfSec = 0; // Количество целых секунд пройденных с запуска таймера
    final private int workingTime; // Количество секунд, которое будет работать таймер
    final private int notificationTime; // Количество секунд через которые таймер будет вызывать notifyAll
    final private Object startLock = new Object(); // Локер старта таймера
    final private Object notifyLock = new Object(); // notify Локер

    public Timer(int workingTime, int notificationTime) {
        this.notificationTime = notificationTime;
        this.workingTime = workingTime;
    }

    /**
     * Устанавливает notifyFlag в false
     */
    public void setNotifyFlagToFalse() {
        notifyFlag = false;
    }

    public boolean getActiveFlag() {
        return activeFlag;
    }

    public boolean getNotifyFlag() {
        return notifyFlag;
    }

    public Object getStartLock() {
        return startLock;
    }

    public Object getNotifyLock() {
        return notifyLock;
    }

    /**
     * Запускает таймер, дает понять всем отслеживающим потокам, что
     * таймер начал свою работу и каждые notificationTime секунд вызывает
     * notifyAll от notifyLock.
     */
    @Override
    public void run() {
        synchronized (startLock) {
            activeFlag = true;
            startLock.notifyAll();
        }
        try {
            while (countOfSec < workingTime) {
                Thread.sleep(1000);
                countOfSec++;
                if (countOfSec % notificationTime == 0) {
                    synchronized (notifyLock) {
                        notifyFlag = true;
                        notifyLock.notifyAll();
                    }
                }
            }
        } catch (InterruptedException ex) {
            activeFlag = false;
            Thread.currentThread().interrupt();
        }
        activeFlag = false;
    }
}
