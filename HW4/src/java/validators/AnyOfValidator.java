package validators;

import annotations.AnyOf;
import validationErrors.AnyOfError;
import validationErrors.ValidationError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Валидатор для проверки аннотации AnyOf
 */
public class AnyOfValidator implements Validator {
    private final ArrayList<String> values; //Допустимые аннотацией значения
    private final String path; //Путь до переданного объекта

    /**
     * Создает валидатор проверки аннотации AnyOf
     * @param annotation проверяемая аннотация AnyOf
     * @param path путь до переданного объекта
     */
    public AnyOfValidator(AnyOf annotation, String path) {
        this.values = new ArrayList<>(Arrays.asList(annotation.value()));
        this.path = path;
    }

    /**
     * Проверяет объект на соответсвие переданной
     * аннотации AnyOf
     * @param object проверяемый объект
     * @return Set найденных ошибок
     */
    @Override
    public Set<ValidationError> validate(Object object) {
        Set<ValidationError> res = new HashSet<>();
        if (object instanceof String || object == null) {
            String str = (String) object;
            if (!values.contains(str))
                res.add(new AnyOfError(values, object, path));
        }
        return res;
    }
}
