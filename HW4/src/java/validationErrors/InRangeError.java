package validationErrors;

public class InRangeError implements ValidationError {
    private final String message; //Сообщение об ошибке
    private final String path; //Путь до поля с ошибкой
    private final Object failedValue; //Значение этого поля
    private final long min; //Минимально допустимое значение
    private final long max; //Максимально допустимое значение

    public InRangeError(long min, long max, Object failedValue, String path) {
        this.min = min;
        this.max = max;
        this.path = path;
        this.failedValue = failedValue;
        this.message = createMessage();
    }

    /**
     * Создает сообщение об ошибке
     * @return сообщеие об ошибке
     */
    private String createMessage() {
        return String.format("must be in range between %d and %d.", min, max);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Object getFailedValue() {
        return failedValue;
    }

    @Override
    public String toString() {
        return String.format("Path: %s\nFailed Value: %s\nMessage: %s",
                getPath(), getFailedValue(), getMessage());
    }
}
