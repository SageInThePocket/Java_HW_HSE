package validators;

import validationErrors.NegativeError;
import validationErrors.ValidationError;

import java.util.HashSet;
import java.util.Set;

/**
 * Валидатор для проверки аннотации Negative
 */
public class NegativeValidator implements Validator {
    private final String path; //Путь до переданного объекта

    /**
     * Создает валидатор проверки аннотации Negative
     * @param path путь до переданного объекта
     */
    public NegativeValidator(String path) {
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
     * аннотации Negative
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

            if (l >= 0) {
                res.add(new NegativeError(object, path));
            }
        }
        return res;
    }
}
