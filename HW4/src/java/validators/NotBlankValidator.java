package validators;

import validationErrors.NotBlankError;
import validationErrors.ValidationError;

import java.util.HashSet;
import java.util.Set;

/**
 * Валидатор для проверки аннотации NotBlank
 */
public class NotBlankValidator implements Validator {
    private final String path; //Путь до переданного объекта

    /**
     * Создает валидатор проверки аннотации NotBlank
     * @param path путь до переданного объекта
     */
    public NotBlankValidator(String path) {
        this.path = path;
    }

    /**
     * Проверяет объект на соответсвие переданной
     * аннотации NotBlank
     * @param object проверяемый объект
     * @return Set найденных ошибок
     */
    @Override
    public Set<ValidationError> validate(Object object) {
        Set<ValidationError> res = new HashSet<>();
        if (object instanceof String) {
            String str = (String) object;
            if (str.isBlank())
                res.add(new NotBlankError(object, path));
        }
        return res;
    }
}
