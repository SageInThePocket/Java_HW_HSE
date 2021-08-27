package validationErrors;

public class PositiveError implements ValidationError {
    private final String message; //Сообщение об ошибке
    private final String path; //Путь до поля с ошибкой
    private final Object failedValue; //Значение этого поля

    public PositiveError(Object failedValue, String path) {
        this.path = path;
        this.failedValue = failedValue;
        this.message = createMessage();
    }

    /**
     * Создает сообщение об ошибке
     * @return сообщеие об ошибке
     */
    private String createMessage() {
        return "number must be positive";
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
