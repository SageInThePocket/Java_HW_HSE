package sample;

import java.util.ArrayList;
import java.util.Collection;

public class InnovatorMartian<T> extends Martian<T> {

    private FamilyTree familyTree; //генеалогическое дерево
    private Martian<T> parent; //родитель
    private Collection<InnovatorMartian<T>> children = new ArrayList<>(); //дети
    private T value; //генетический код

    /**
     * Создает новатора по генетическому коду, родителю и коллекции детей
     * @param value генетический код
     * @param parent родитель
     * @param children коллекция детей
     */
    public InnovatorMartian(T value, Martian<T> parent, Collection<InnovatorMartian<T>> children) {
        //Проверка корректности переданных параметров
        if (value == null)
            throw new IllegalArgumentException("Генетический код был равен null");
        //Проверяем что дети не null
        if (children == null)
            throw new IllegalArgumentException("Переданный список детей был null");
        //Проверяем наличие ! в ген. коде типа string
        if (value instanceof String && ((String)value).contains("!"))
            throw new IllegalArgumentException("Генетический код не может содержать \"!\"");
        //Проверяем наличие марсианина с таким же ген. кодом
        boolean hasValueInFamily = parent != null && parent.familyHasValue(value);
        if (existingValues.contains(value) && !hasValueInFamily)
            throw new IllegalArgumentException("Марсианин с генетичесиким кодом "
                    + value + " существует вне семьи");
        if (!hasValueInFamily) // добавляем value в список существующих value
            existingValues.add(value);
        //Инициализация параметров
        this.value = value;
        if (!(parent instanceof InnovatorMartian) && parent != null)
            throw new IllegalArgumentException("В качестве отца был передан консерватор");
        setParent((InnovatorMartian<T>) parent);
        //Заполнение списка детей
        for (InnovatorMartian<T> child: children)
            if (!addChild(child))
                throw new IllegalArgumentException("Был передан некорректный список детей");
        familyTree = new FamilyTree(this);
    }

    /**
     * Создает новатора по генетическому коду, родителю, коллекции детей и генеалогическому дереву
     * @param value генетический код
     * @param parent родитель
     * @param children коллеция детей
     * @param familyTree генеалогическое дерево
     */
    InnovatorMartian(T value, Martian<T> parent, ArrayList<InnovatorMartian<T>> children, FamilyTree familyTree) {
        //Проверка корректности переданных параметров
        if (value == null)
            throw new IllegalArgumentException("Генетический код был равен null");
        if (children == null)
            throw new IllegalArgumentException("Переданный список детей был null");
        if (value instanceof String && ((String)value).contains("!"))
            throw new IllegalArgumentException("Генетический код не может содержать \"!\"");
        //Проверяем наличие марсианина с таким же ген. кодом
        boolean hasValueInFamily = parent != null && parent.familyHasValue(value);
        if (existingValues.contains(value) && !hasValueInFamily)
            throw new IllegalArgumentException("Марсианин с генетичесиким кодом "
                    + value + " существует вне семьи");
        if (!hasValueInFamily) // добавляем value в список существующих value
            existingValues.add(value);
        //Инициализация параметров
        this.value = value;
        if (!(parent instanceof InnovatorMartian) && parent != null)
            throw new IllegalArgumentException("В качестве отца был передан консерватор");
        setParent((InnovatorMartian<T>) parent);
        //Заполнение списка детей
        this.children = new ArrayList<>();
        for (Martian<T> child: children)
            if (child instanceof InnovatorMartian)
                addChild((InnovatorMartian<T>) child);
            else
                throw new IllegalArgumentException("В качестве ребенка был передан ConservativeMartian");
        this.familyTree = familyTree;
    }

    /**
     * Удаляет всех детей объекта
     */
    private void deleteAllChildren() {
        ArrayList<Martian<T>> children = new ArrayList<>(this.children);
        int i = 0;
        while (this.children.size() > 0) {
            if(!deleteChild((InnovatorMartian<T>) children.get(i)))
                throw new IllegalArgumentException("Ошибка: невозможно удалить ребенка");
            i++;
        }
    }

    /**
     * Устанавливает новый генетический код
     * @param value новое значение генетического кода
     */
    public void setValue(T value) {
        //Проверка корректности переданного значения
        if (value instanceof String) {
            if (((String) value).length() > 256)
                throw new IllegalArgumentException("Длинна генетического кода не " +
                        "может быть больше 256 символов");
            if (((String)value).contains("!"))
                throw new IllegalArgumentException("Генетический код не может содержать \"!\"");
        }
        this.value = value;
    }

    /**
     * Устанавливает родителя
     * @param parent устанавливаемый родитель
     */
    public void setParent(InnovatorMartian<T> parent) {
        if (parent != null) {
            if (parent.equals(this))
                throw new SetParentException("Невозможно сделать себя родителем");
            if (hasDescendant(parent)) //проверка наличия parent в потомках объекта
                throw new SetParentException("Невозможно сделать потомка родителем");
            //проверка наличия в коллекции детей нового родителя
            if (!parent.hasChild(this))
                parent.addChild(this);
        }
        if (parent != this.parent) {
            //удаляем прошлого родителя и удаляем объект из коллекции детей родителя этого объекта
            if (this.parent != null && this.parent.hasChildWithValue(value))
                ((InnovatorMartian<T>) this.parent).deleteChild(this);
            this.parent = parent;
        }
    }

    /**
     * Устанавливает новый список потомков
     * @param descendants новые дети
     */
    public void setDescendants(Collection<InnovatorMartian<T>> descendants) {
        if (descendants == null)
            throw new SetDescendantsException("Коллекция была null");
        for (InnovatorMartian<T> descendant: descendants) {
            if (hasAncestor(descendant)) //проверка на принадлежность предкам
                throw new SetDescendantsException("Невозможно добавить своего предка в потомки");
            if (descendant == null) //проверка на принадлежность предкам
                throw new SetDescendantsException("Был передан null");
        }
        for (InnovatorMartian<T> descendant: descendants)
            descendant.setParent(null); //удаляем родителей у переданных мерсиан
        deleteAllChildren(); //удаляем всех детей у себя
        for (InnovatorMartian<T> descendant: descendants)
                if(!addChild(descendant))//добавляем новых детей
                    throw new AddChildException("Не удалось добавить марсианина " +
                            descendant + " в список детей", descendant);
        if (familyTree != null)
            familyTree.update();
    }

    /**
     * Проверяет можно ли добавить ребенка к марсианину и если можно, то
     * добавляет его.
     * @param child добавляемый ребенок
     * @return True если ребенок успешно добавлен и False если ребенка
     * невозможно добавить
     */
    public boolean addChild(InnovatorMartian<T> child) {
        if (child == null)
            return false;
        if (hasAncestor(child))
            return false;
        if (hasChild(child))
            return false;
        children.add(child);
        //удаляем ребенка из коллекции детей его родителя
        if (child.parent == null || !child.parent.equals(this)) {
            if (child.parent != null)
                ((InnovatorMartian<T>)child.parent).deleteChild(child);
            child.setParent(this); //устанавниваем нового родителя
        }
        if (familyTree != null)
            familyTree.update(); //обновляем дерево объекта
        return true;
    }

    /**
     * Проверяет можно ли удалить ребенка из списка детей и если можно, то
     * удаляет его.
     * @param child удаляемый ребенок
     * @return True если ребенок успешно удален и False если ребенка
     * невозможно удалить
     */
    public boolean deleteChild(InnovatorMartian<T> child) {
        if (!hasChild(child))
            return false;
        children.remove(child);
        child.setParent(null);
        if (familyTree != null)
            familyTree.update();
        return true;
    }

    /**
     * @return генетический код марсианина
     */
    @Override
    public T getValue() {
        return value;
    }

    /**
     * @return родитель марсианина
     */
    @Override
    public Martian<T> getParent() {
        return parent;
    }

    /**
     * @return список детей марсианина
     */
    @Override
    public Collection<Martian<T>> getChildren() {
        return new ArrayList<>(children);
    }

    /**
     * @return список потомков марсианина
     */
    @Override
    public Collection<Martian<T>> getDescendants() {
        Collection<Martian<T>> descendants = getChildren();
        for (Martian<T> child: getChildren()) {
            descendants.addAll(child.getDescendants());
        }
        return descendants;
    }

    /**
     * @return генеалогическое дерево марсианина, начинающиеся с него
     */
    @Override
    public FamilyTree getFamilyTree() {
        return familyTree;
    }

    @Override
    public String toString() {
        return "InnovatorMartian" + super.toString();
    }
}
