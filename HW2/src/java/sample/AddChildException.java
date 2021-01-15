package sample;

public class AddChildException extends IllegalArgumentException {

    private Martian martian; //Марсианин при добавлении которого произошла ошибка

    /**
     * Создает исключение AddChildException без параметров
     */
    public AddChildException() {
        super();
    }

    /**
     * Создает исключение AddChildException с сообщением
     * @param msg сообщение
     */
    public AddChildException(String msg) {
        super(msg);
    }

    /**
     * Создает исключение AddChildException с сообщением и марсианином, при добавлении которого
     * возникло исключение
     * @param msg сообщение
     * @param martian марсианин при добавлении которого возникла ошибка
     */
    public AddChildException(String msg, Martian martian) {
        super(msg);
        this.martian = martian;
    }

    /**
     * @return марсианин из-за которого произошла ошибка
     */
    public Martian getMartian() {
        return martian;
    }
}
