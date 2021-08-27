package validators;

import annotations.InRange;
import validationErrors.InRangeError;
import validationErrors.ValidationError;

import java.util.HashSet;
import java.util.Set;

/**
 * Валидатор для проверки аннотации InRange
 */
public class InRangeValidator implements Validator {
    private final long min; //Минимально допустимое значение
    private final long max; //Максимально допустимое значение
    private final String path; //Путь до переданного объекта

    /**
     * Создает валидатор проверки аннотации InRange
     * @param annotation проверяемая аннотация InRange
     * @param path путь до переданного объекта
     */
    public InRangeValidator(InRange annotation, String path) {
        this.min = annotation.min();
        this.max = annotation.max();
        this.path = path;
    }

    /**
     * Проверяет тип объекта
     * @param object проверяемый объект
     * @return True - тип объекта является подходящим;
     * False - тип объекта не подходит
     */
    private static boolean isSuitableType(Object object) {
        return object instanceof Byte || object instanceof Short ||
               object instanceof Integer || object instanceof Long;
    }

    /**
     * Проверяет объект на соответсвие переданной
     * аннотации InRange
     * @param object проверяемый объект
     * @return Set найденных ошибок
     */
    @Override
    public Set<ValidationError> validate(Object object) {
        Set<ValidationError> res = new HashSet<>();
        if (isSuitableType(object)) {
            long l;
            if (object instanceof Byte)
                l = (byte) object;
            else if (object instanceof Short)
                l = (short) object;
            else if (object instanceof Integer)
                l = (int) object;
            else
                l = (long) object;

            if (l < min || l > max)
                res.add(new InRangeError(min, max, object, path));
        }
        return res;
    }
}
