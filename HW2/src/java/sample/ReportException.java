package sample;

public class ReportException extends IllegalArgumentException {

    /**
     * Создает исключение ReportException
     */
    public ReportException() {
        super();
    }

    /**
     * Создает исключение ReportException с сообщением
     * @param msg сообщение
     */
    public ReportException(String msg) {
        super(msg);
    }

}
