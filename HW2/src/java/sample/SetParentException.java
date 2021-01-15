package sample;

public class SetParentException extends RuntimeException {
    /**
     * Создает исключение SetParentException без параметров
     */
    public SetParentException() {
        super();
    }

    /**
     * Создает исключение SetParentException с сообщением
     * @param msg сообщение
     */
    public SetParentException(String msg) {
        super(msg);
    }
}
