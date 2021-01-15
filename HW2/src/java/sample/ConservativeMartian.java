package sample;

import java.util.ArrayList;
import java.util.Collection;


public class ConservativeMartian<T> extends Martian<T>{

    final private T value; //генетический код
    final private FamilyTree familyTree; //генеалогическое дерево
    final private ConservativeMartian<T> parent; //родитель
    final private Collection<ConservativeMartian<T>> children = new ArrayList<>(); //дети
    final private Collection<ConservativeMartian<T>> descendants = new ArrayList<>(); //потомки

    /**
     * Создает конесерватора по объекту новатора и устанавливает родителя parent
     * @param martian новатор на основе которого создается консерватор
     * @param parent родитель
     */
    private ConservativeMartian(InnovatorMartian<T> martian, ConservativeMartian<T> parent) {
        this.value = martian.getValue();
        this.parent = parent;
        for (Martian<T> child: martian.getChildren())
            addChild(new ConservativeMartian<T>((InnovatorMartian<T>)child, this));
        familyTree = new FamilyTree(this);
        for (Martian<T> child: children){
            descendants.add((ConservativeMartian<T>) child);
            descendants.addAll(((ConservativeMartian<T>) child).descendants);
        }
    }

    /**
     * Создает консерватора по объекту новатора
     * @param martian новатор на основе которого создается консерватор
     */
    public ConservativeMartian(InnovatorMartian<T> martian) {
        this.value = martian.getValue();
        this.parent = null;
        for (Martian<T> child: martian.getChildren())
            addChild(new ConservativeMartian<>((InnovatorMartian<T>)child, this));
        familyTree = new FamilyTree(this);
        for (Martian<T> child: children) {
            descendants.add((ConservativeMartian<T>) child);
            descendants.addAll(((ConservativeMartian<T>) child).descendants);
        }
    }

    /**
     * Проверяет можно ли добавить ребенка и если можно, то добавляет
     * его в коллекцию детей
     * @param child добавляемый ребенок
     */
    private void addChild(ConservativeMartian<T> child) {
        if (child == null)
            throw new IllegalArgumentException("В качестве ребенка был передан null");
        if (child.getParent() != null && !child.getParent().equals(this))
            throw new IllegalArgumentException("Невозможно добавить ребенка так как у него другой отец");
        if (familyHasMartian(child))
            throw new IllegalArgumentException("В семье уже есть марсианин " + child);
        for (Martian<T> descendant: child.getDescendants())
            if (familyHasMartian(descendant))
                throw new IllegalArgumentException("В потомках добавляемого марсианина найдены члены семьи");
        children.add(child);
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
        return new ArrayList<>(descendants);
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
        return "ConservativeMartian" + super.toString();
    }
}
