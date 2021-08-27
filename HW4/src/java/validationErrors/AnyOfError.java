package validationErrors;

import java.util.ArrayList;

public class AnyOfError implements ValidationError {
    private final String message; //Сообщение об ошибке
    private final String path; //Путь до поля с ошибкой
    private final Object failedValue; //Значение этого поля
    private final ArrayList<String> values; //Допустимые значения

    public AnyOfError (ArrayList<String> values, Object failedValue, String path) {
        this.values = values;
        this.path = path;
        this.failedValue = failedValue;
        message = createMessage();
    }

    /**
     * Создает сообщение об ошибке
     * @return сообщеие об ошибке
     */
    private String createMessage() {
        return String.format("value must be one of %s.", valuesToString());
    }

    /**
     * Преобразует массив допустимых значений в понятный
     * пользователю вид
     * @return перечисленные через запятую допустимые значения
     */
    private String valuesToString() {
        String res = "";
        for (String value: values)
            res += String.format("'%s', ",value);
        if (values.size() > 0)
            res = res.substring(0, res.length() - 2);
        return res;
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
        if (failedValue == null)
            return String.format("Path: %s\nFailed Value: %s\nMessage: %s",
                getPath(), getFailedValue(), getMessage());
        return String.format("Path: %s\nFailed Value: \"%s\"\nMessage: %s",
                getPath(), getFailedValue(), getMessage());
    }
}
