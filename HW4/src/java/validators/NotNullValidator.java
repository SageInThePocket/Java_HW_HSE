package validators;

import validationErrors.NotNullError;
import validationErrors.ValidationError;

import java.util.HashSet;
import java.util.Set;

/**
 * Валидатор для проверки аннотации NotNull
 */
public class NotNullValidator implements Validator {
    private final String path; //Путь до переданного объекта

    /**
     * Создает валидатор проверки аннотации NotNull
     * @param path путь до переданного объекта
     */
    public NotNullValidator(String path) {
        this.path = path;
    }

    /**
     * Проверяет объект на соответсвие переданной
     * аннотации NotNull
     * @param object проверяемый объект
     * @return Set найденных ошибок
     */
    @Override
    public Set<ValidationError> validate(Object object) {
        Set<ValidationError> res = new HashSet<>();
        if (object == null)
            res.add(new NotNullError(path));
        return res;
    }
}
