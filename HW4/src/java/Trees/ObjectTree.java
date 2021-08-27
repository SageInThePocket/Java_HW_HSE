package Trees;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Формирует дерево объекта, содержащее
 * все поля этого объекта TypeTree каждого
 * поля.
 * Позволяет довольно просто работать с объектом
 * и его полями.
 */
public class ObjectTree {
    private final HashMap<String, TypeTree> fieldsType; //TypeTree для каждого поля класса
    private final ArrayList<Field> fields; //Поля класса переденного экземпляря
    private final String parentPath; //

    /**
     * Создает дерево переданного объекта
     * @param obj объект по которому будет выстраиватся дерево
     * @param parentPath путь родителя
     */
    public ObjectTree(Object obj, String parentPath) {
        this.parentPath = parentPath;
        fieldsType = new HashMap<>();
        fields = new ArrayList<>();

        //Получаем нужную информацию о полях
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f: fields) {
            this.fields.add(f);
            AnnotatedType apType = f.getAnnotatedType();
            fieldsType.put(f.getName(), new TypeTree(apType, getFieldPath(f.getName())));
        }
    }


    /**
     * Возвращает TypeTree поля по его названию
     * @param fieldName название нужного поля
     * @return TypeTree поля
     */
    public TypeTree getTypeTree(String fieldName) {
        return fieldsType.get(fieldName);
    }

    /**
     * Возвращает поле класса по имени поля
     * @param fieldName имя поля
     * @return поле класса с именем fieldName
     */
    public String getFieldPath(String fieldName) {
        if (parentPath.isEmpty())
            return fieldName;
        return String.format("%s.%s", parentPath, fieldName);
    }

    /**
     * Возвращает список полей класса
     * @return поля класса
     */
    public ArrayList<Field> getFields() {
        return fields;
    }

}
