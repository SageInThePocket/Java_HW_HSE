package validationErrors;

public class NotEmptyError implements ValidationError {
    private final String message; //Сообщение об ошибке
    private final String path; //Путь до поля с ошибкой
    private final Object failedValue; //Значение этого поля

    public NotEmptyError(Object failedValue, String path) {
        this.path = path;
        this.failedValue = failedValue;
        this.message = createMessage();
    }

    /**
     * Создает сообщение об ошибке
     * @return сообщеие об ошибке
     */
    private String createMessage() {
        if (failedValue instanceof String)
            return "string must not be empty";
        else
            return "at least some value must be specified";
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
