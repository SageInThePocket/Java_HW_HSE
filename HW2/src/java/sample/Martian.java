package sample;

import java.util.ArrayList;
import java.util.Collection;

abstract public class Martian<T> {

    static ArrayList existingValues = new ArrayList();

    /**
     * Отчищает лист существующих значений
     */
    static void clearExistingValues() {
        existingValues = new ArrayList();
    }

    /**
     * @return Коллекция всех членов семьи кроме потомков передаваемого
     * марсианина
     */
    protected Collection<Martian<T>> getAllWithoutDescendants() {
        Collection<Martian<T>> notDescendants = getFirstMartian().getDescendants();
        notDescendants.removeAll(getDescendants());
        return notDescendants;
    }

    /**
     * Проверяет наличие марсианина в семье
     * @param martian проверяемый марсианин
     * @return True если марсианин есть в семье и
     * False в ином случае
     */
    protected boolean familyHasMartian(Martian<T> martian) {
        return getFirstMartian().hasDescendant(martian) || getFirstMartian().equals(martian);
    }

    /**
     * Проверяет наличие марсианина с генетическим ключем в семье
     * @param value генетичечкий ключ
     * @return True если марсианин с ген. ключем есть в семье и
     * False в ином случае
     */
    protected boolean familyHasValue(T value) {
        return getFirstMartian().hasDescendantWithValue(value)
                || getFirstMartian().getValue().equals(value);
    }

    /**
     * Проверяет наличие переданного марсианина в предках
     * @param martian искомый в предках марсианин
     * @return True если марсианин есть в предках и False
     * в ином случае
     */
    protected boolean hasAncestor(Martian<T> martian) {
        if (!this.equals(martian))
            if (getParent() != null)
                return getParent().hasAncestor(martian);
            else
                return false;
        else
            return true;
    }

    /**
     * @return генетический код марсианина
     */
    abstract public T getValue();
    /**
     * @return родитель марсианина
     */
    abstract public Martian<T> getParent();
    /**
     * @return список детей марсианина
     */
    abstract public Collection<Martian<T>> getChildren();
    /**
     * @return список потомков марсианина
     */
    abstract public Collection<Martian<T>> getDescendants();
    /**
     * @return генеалогическое дерево марсианина, начинающиеся с него
     */
    abstract public FamilyTree getFamilyTree();

    /**
     * @return самый первый марсианин в семье (родитель которого null)
     */
    public Martian<T> getFirstMartian() {
        if (getParent() != null)
            return getParent().getFirstMartian(); //рекурсивно идет вверх по дереву
        else
            return this;
    }

    /**
     * Проверяет наличие ребенка генетический ключ которого
     * равен переданному значению value
     * @param value генетический ключ искомого ребенка
     * @return True если марсианин с генетическим ключем value есть
     * в детях и False в ином случае
     */
    public boolean hasChildWithValue(T value) {
        return getChildren().stream().anyMatch(x -> x.getValue().equals(value));
    }

    /**
     * Проверяет наличие переданного ребенка
     * @param martian искомый ребенок
     * @return True если марсианин есть в коллеции детей и
     * False в ином случае
     */
    public boolean hasChild(Martian<T> martian) {
        return getChildren().stream().anyMatch(x -> x.equals(martian));
    }

    /**
     * Проверяет наличие предка генетический ключ которого
     * равен переданному значеию value
     * @param value генетический ключ искомого предка
     * @return True если марсианин с генетическим ключем value есть
     * в потомках и False в ином случае
     */
    public boolean hasDescendantWithValue(T value) {
        return getDescendants().stream().anyMatch(x -> x.getValue().equals(value));
    }

    /**
     * Проверяет наличие переданного потомка
     * @param martian искомый ребенок
     * @return True если марсианин есть в коллеции потомков и
     * False в ином случае
     */
    public boolean hasDescendant(Martian<T> martian) {
        return getDescendants().stream().anyMatch(x -> x.equals(martian));
    }

    /**
     * Заоплняет переданную коллекцию прдеками марсианина (в список также добавляется он сам)
     * @param collection заполняемая предками коллекция
     * @return заполненная коллекция
     */
    public Collection<Martian<T>> getAncestors(Collection<Martian<T>> collection) {
        collection.add(this);
        if (getParent() == null)
            return collection;
        return getParent().getAncestors(collection);
    }

    @Override
    public String toString() {
        String className = getValue().getClass().getSimpleName();
        return String.format("(%s:%s)", className, getValue());
    }
}
