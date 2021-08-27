package validationErrors;

public class NotBlankError implements ValidationError {
    private final String message; //Сообщение об ошибке
    private final String path; //Путь до поля с ошибкой
    private final Object failedValue; //Значение этого поля

    public NotBlankError(Object failedValue, String path) {
        this.path = path;
        this.failedValue = failedValue;
        this.message = createMessage();
    }

    /**
     * Создает сообщение об ошибке
     * @return сообщеие об ошибке
     */
    private String createMessage() {
        return "string must not be blank";
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
        return String.format("Path: %s\nFailed Value: \"%s\"\nMessage: %s",
                getPath(), getFailedValue(), getMessage());
    }
}
