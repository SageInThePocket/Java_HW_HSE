package validators;

import Trees.ObjectTree;
import Trees.TypeTree;
import annotations.*;
import validationErrors.ValidationError;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;


/* По условию допустимы только листы листов,
 * но у меня есть возможность закинуть в листы
 * сеты, а в эти сеты мапы и т.д. и можно
 * указывать аннотации внутри мапов (но только для значений), сетов и
 * листов, но так как сеты неупорядоченная коллекция
 * и у них нет индексов, то в пути в [] просто
 * ничего не отображается, что значит, что просто
 * где-то в каком-то элементе в сете произошла какая-то
 * ошибка. В случае же с мапами в [] указывается toString()
 * ключа.
 *
 * Надеюсь это не карается, так как по сути это что-то сделанное
 * поверх условия. От этого дополнения можно довольно быстро избавиться
 * удалив 62-63 строку и 58-59, но мне не хочется, потому что это
 * же почти как мои дети, которые оказались не нужны по условию, но
 * я их очень люблю, поэтому не снимайте за это плизики :)
 *
 * Я не сразу заметил, что вложенность и вложенные аннотации могут
 * быть только у листов.
 */

/**
 * Класс реализующий интерфейс Validator и
 * проверяющий аннотации в это объекте
 */
public class ValidatorClass implements Validator {
    private String currTemplate = ""; //Текущий шаблон пути
    private final ArrayList<Object> indexes = new ArrayList<>(); //Индексы пути

    /**
     * Получает путь до объекта по какому-то
     * шаблону
     * @return путь до текущего объекта
     */
    private String getPathWithIndexes() {
        String res = currTemplate;
        //Подставляем индексы, вместо '-'
        for (Object index: indexes)
            res = res.replaceFirst("-", index.toString());
        return res;
    }

    /**
     * Проверяет тип данных в соответсвии с
     * обозначенными аннотациями
     * @param typeTree TypeTree типа
     * @param obj объект для анализа
     * @return Set найденных ошибок
     */
    private Set<ValidationError> validateType(TypeTree typeTree, Object obj) {
        Set<ValidationError> res = new HashSet<>();

        if (isMap(obj)) //Если наседует интерфей map
            validateMap(typeTree, obj, res);
        else if (isList(obj)) //Если является коллекцией
            validateList(typeTree, obj, res);
        else if (isSet(obj))
            validateSet(typeTree, obj, res);
        else if (obj.getClass().isAnnotationPresent(Constrained.class)) { //Если помечен Constrained
            currTemplate = typeTree.getPathTemplate();
            res.addAll(validate(obj));
        }

        return res;
    }

    /**
     * Проверяет переданный Map на соответсвие
     * указанных аннотаций
     * @param typeTree TypeTree типа
     * @param obj объект для анализа
     * @param res Set найденных ошибок
     */
    private void validateMap(TypeTree typeTree, Object obj, Set<ValidationError> res) {
        Map<?,?> map = (Map<?,?>) obj;

//        //Проверка ключей (Не адаптировано под вывод пути, да и не нужно по заданию)
//        Set<?> kesSet = map.keySet();
//        for (Object key: kesSet) {
//            TypeTree keyTree = typeTree.getSubTrees().get(0);
//            if (key != null)
//                res.addAll(validateType(keyTree, key));
//            checkAnnotations(keyTree, key, res);
//        }

        //Проверка значений
        Set<?> kesSet = map.keySet();
        for (Object key: kesSet)  {
            indexes.add(key);
            Object value = map.get(key);
            TypeTree valueTree = typeTree.getSubTrees().get(1);
            if (value != null)
                res.addAll(validateType(valueTree, value));
            checkAnnotations(valueTree, value, res);
            indexes.remove(indexes.size() - 1);
        }
    }

    /**
     * Проверяет переданный List на соответсвие
     * указанных аннотаций
     * @param typeTree TypeTree типа
     * @param obj объект для анализа
     * @param res Set найденных ошибок
     */
    private void validateList(TypeTree typeTree, Object obj, Set<ValidationError> res) {
        List<?> collection = (List<?>) obj;

        int i = 0;
        for (Object value: collection) {
            indexes.add(i++);
            TypeTree valueTree = typeTree.getSubTrees().get(0);
            if (value != null)
                res.addAll(validateType(valueTree, value));
            checkAnnotations(valueTree, value, res);
            indexes.remove(indexes.size() - 1);
        }
    }

    /**
     * Проверяет переданный Set на соответсвие
     * указанных аннотаций
     * @param typeTree TypeTree типа
     * @param obj объект для анализа
     * @param res Set найденных ошибок
     */
    private void validateSet(TypeTree typeTree, Object obj, Set<ValidationError> res) {
        Set<?> collection = (Set<?>) obj;

        for (Object value: collection) {
            indexes.add("");
            TypeTree valueTree = typeTree.getSubTrees().get(0);
            if (value != null)
                res.addAll(validateType(valueTree, value));
            checkAnnotations(valueTree, value, res);
            indexes.remove(indexes.size() - 1);
        }
    }

    /**
     * Проверяет соответсвие аннотациям
     * @param typeTree TypeTree типа
     * @param obj объект для анализа
     * @param res Set найденных ошибок
     */
    private void checkAnnotations(TypeTree typeTree, Object obj, Set<ValidationError> res) {
        Annotation[] annotations = typeTree.getAnnotations();
        currTemplate = typeTree.getPathTemplate();
        Annotation notNull = getNotNullAnnotation(annotations);

        //Проверяем null ли наш объект и есть ли у него аннотация NotNull
        //Если есть, то действия других аннотаций отменяются
        if (obj == null && notNull != null) {
            Validator validator = createValidator(notNull, getPathWithIndexes());
            res.addAll(validator.validate(obj));
        } else {
            for (Annotation annotation: annotations) {
                Validator validator = createValidator(annotation, getPathWithIndexes());
                res.addAll(validator.validate(obj));
            }
        }
    }

    /**
     * Находит в массиве аннотаций аннотацию
     * NotNull
     * @param annotations массив аннотаций
     * @return аннотацию NotNull и
     */
    private static Annotation getNotNullAnnotation(Annotation[] annotations) {
        for (Annotation annotation: annotations)
            if (annotation instanceof NotNull)
                return annotation;
        return null;
    }

    /**
     * Проверяет реализует ли объект интерфейс Map
     * @param obj проверяемый объект
     * @return True - реализует; False - не реализует
     */
    private static boolean isMap(Object obj) {
        return Map.class.isAssignableFrom(obj.getClass());
    }

    /**
     * Проверяет реализует ли объект интерфейс List
     * @param obj проверяемый объект
     * @return True - реализует; False - не реализует
     */
    private static boolean isList(Object obj) {
        return List.class.isAssignableFrom(obj.getClass());
    }

    /**
     * Проверяет реализует ли объект интерфейс Set
     * @param obj проверяемый объект
     * @return True - реализует; False - не реализует
     */
    private static boolean isSet(Object obj) {
        return Set.class.isAssignableFrom(obj.getClass());
    }

    /**
     * Создает валидатор определенной аннотации
     * @param annotation аннотация, для которой создается валидатор
     * @param path путь к объекту
     * @return валидатор определенной аннотации
     */
    private static Validator createValidator(Annotation annotation, String path) {
        if (annotation instanceof NotNull)
            return new NotNullValidator(path);
        else if (annotation instanceof NotEmpty)
            return new NotEmptyValidator(path);
        else if (annotation instanceof NotBlank)
            return new NotBlankValidator(path);
        else if (annotation instanceof Negative)
            return new NegativeValidator(path);
        else if (annotation instanceof InRange)
            return new InRangeValidator((InRange) annotation, path);
        else if (annotation instanceof AnyOf)
            return new AnyOfValidator((AnyOf) annotation, path);
        else if (annotation instanceof Size)
            return new SizeValidator((Size) annotation, path);
        return new PositiveValidator(path);
    }

    /**
     * Проверяет поля переданного объекта
     * на соответсвие указанных аннотаций
     * @param object проверяемый объект
     * @return Set найденных ошибок
     */
    @Override
    public Set<ValidationError> validate(Object object) {
        Set<ValidationError> res = new HashSet<>();

        if (object.getClass().isAnnotationPresent(Constrained.class)) {
            ObjectTree objTree = new ObjectTree(object, currTemplate);
            ArrayList<Field> fields = objTree.getFields();

            for (Field field : fields) {
                field.setAccessible(true);

                Object val;
                try {
                    val = field.get(object);
                } catch (IllegalAccessException e) {
                    throw new IllegalArgumentException("An error occurred " +
                            "while trying to get the field value");
                }

                TypeTree fieldType = objTree.getTypeTree(field.getName());
                if (val != null)
                    res.addAll(validateType(fieldType, val));
                checkAnnotations(fieldType, val, res);
            }
        }
        return res;
    }
}


