package Trees;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.util.ArrayList;

/**
 * Строит дерево типов корнем которого
 * является некоторый тип, а его детьми
 * являются типы из generic-а.
 * Хранит в себе аннотации типа, спиоск
 * детей этого типа и шаблон пути до
 * данного типа
 */
public class TypeTree {
    private final Annotation[] annotations; //Анотации типа
    private final ArrayList<TypeTree> subTrees; //Поддеревья (дети)
    private final String pathTemplate; //Шаблон пути

    /**
     * Создает TypeTree получая всю нужную информацию
     * о типе данных
     * @param annotatedType тип
     * @param pathTemplate шаблон пути данного типа
     */
    public TypeTree(AnnotatedType annotatedType, String pathTemplate) {
        this.pathTemplate = pathTemplate;
        annotations = annotatedType.getAnnotations();
        //Заполняем список детей
        if (annotatedType instanceof AnnotatedParameterizedType) { //Проверяем, что наш тип является generic
            subTrees = new ArrayList<>();
            AnnotatedType[] aTypes = ((AnnotatedParameterizedType) annotatedType).getAnnotatedActualTypeArguments();
            for (AnnotatedType t: aTypes)
                subTrees.add(new TypeTree(t, getChildPath()));
        } else
            subTrees = null;
    }

    /**
     * Возвращает ArrayList аннотаций типа
     * @return ArrayList аннотаций типа
     */
    public Annotation[] getAnnotations() {
        return annotations;
    }

    /**
     * Возвращает ArrayList поддеревьев (детей) типа
     * @return ArrayList поддеревьев (детей) типа
     */
    public ArrayList<TypeTree> getSubTrees() {
        return subTrees;
    }

    /**
     * Возвращает шаблон пути типа
     * @return шаблон пути типа
     */
    public String getPathTemplate() {
        return pathTemplate;
    }

    /**
     * Получает шаблон пути ребенка
     * @return шаблон пути ребенка
     */
    public String getChildPath() {
        return String.format("%s[-]", pathTemplate);
    }
}
