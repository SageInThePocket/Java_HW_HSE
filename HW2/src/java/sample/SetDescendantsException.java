package sample;

public class SetDescendantsException extends RuntimeException {
    /**
     * Создает исключение SetDescendantsException без параметров
     */
    public SetDescendantsException() {
        super();
    }

    /**
     * Создает исключение SetDescendantsException с сообщением
     * @param msg сообщение
     */
    public SetDescendantsException(String msg) {
        super(msg);
    }
}
