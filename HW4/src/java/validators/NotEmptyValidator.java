package validators;

import validationErrors.NotEmptyError;
import validationErrors.ValidationError;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Валидатор для проверки аннотации NotEmpty
 */
public class NotEmptyValidator implements Validator {
    private final String path; //Путь до переданного объекта

    /**
     * Создает валидатор проверки аннотации NotEmpty
     * @param path путь до переданного объекта
     */
    public NotEmptyValidator(String path) {
        this.path = path;
    }

    /**
     * Преобразует объект в коллекцию
     * @param object преобразуеммый объект
     * @return Collection если это string,
     * list, set или map. В случае если это невозможно -
     * возвращает null
     */
    private static Collection<?> toCollection(Object object) {
        if (object != null) {
            if (List.class.isAssignableFrom(object.getClass()) ||
                    Set.class.isAssignableFrom(object.getClass()))
                return (Collection<?>) object;
            else if (Map.class.isAssignableFrom(object.getClass()))
                return ((Map<?, ?>) object).keySet();
            else if (object instanceof String)
                return ((String) object).chars().mapToObj((i) ->
                        (char) i).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * Проверяет объект на соответсвие переданной
     * аннотации NotEmpty
     * @param object проверяемый объект
     * @return Set найденных ошибок
     */
    @Override
    public Set<ValidationError> validate(Object object) {
        Set<ValidationError> res = new HashSet<>();

        Collection<?> collection = toCollection(object);
        if (collection != null)
            if (collection.isEmpty())
                res.add(new NotEmptyError(object, path));

        return res;
    }
}
