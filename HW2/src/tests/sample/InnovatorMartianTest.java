package sample;

import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InnovatorMartianTest {

    private static String report;
    private InnovatorMartian<Integer> martian;
    private FamilyTree ft;

    private static String readAllText(String filePath) {
        String res = "";
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);
            while (br.ready()) {
                res += br.readLine() + "\n";
            }
        } catch (IOException e) {
            System.out.println("При чтении фала произошла ошибка: " + e.getMessage());
        }
        return res;
    }

    @BeforeAll
    static void beforeAll() {
        report = readAllText("reports\\report1.txt");
    }

    @BeforeEach
    void setUp() {
        Martian.clearExistingValues(); //очищаем лист существуюших значений
        ft = FamilyTree.createTreeByReport(report);
        martian = (InnovatorMartian<Integer>) ft.getMartianNode();
    }


    @Test
    void setParent() {
        InnovatorMartian<Integer> newMartian = new InnovatorMartian<>(106, null, new ArrayList<>());
        //Устанавливаем только что созданного марсианина в качества отца
        martian.setParent(newMartian);
        assertEquals(newMartian.getFamilyTree().toString(), readAllText("results\\setParent\\result1.txt"));
        //Устанавливаем в качестве отца своего ребенка
        InnovatorMartian<Integer> child = (InnovatorMartian<Integer>) newMartian.getChildren().toArray()[0];
        SetParentException e = assertThrows(SetParentException.class, () -> newMartian.setParent(child));
        assertEquals(e.getMessage(), "Невозможно сделать потомка родителем");
        //Устанавливаем в качестве отца своего внука
        InnovatorMartian<Integer> grandchild = (InnovatorMartian<Integer>) child.getChildren().toArray()[1];
        e = assertThrows(SetParentException.class, () -> newMartian.setParent(grandchild));
        assertEquals(e.getMessage(), "Невозможно сделать потомка родителем");
        //Устанавливаем внуку своего прадеда в качестве отца
        InnovatorMartian<Integer> grandchild2 = (InnovatorMartian<Integer>) grandchild.getChildren().toArray()[0];
        grandchild2.setParent(newMartian);
        assertEquals(newMartian.getFamilyTree().toString(), readAllText("results\\setParent\\result2.txt"));
        //Устанавливаем себя в качестве отца
        e = assertThrows(SetParentException.class, () ->  newMartian.setParent(newMartian));
        assertEquals(e.getMessage(), "Невозможно сделать себя родителем");
    }

    @Test
    void setDescendants() {
        ArrayList<InnovatorMartian<Integer>> descendantsList = new ArrayList<>();
        //Создаем список с потомками, которых можно будет добавить InnovatorMartian(Integer:9)
        descendantsList.add((InnovatorMartian<Integer>) martian.getDescendants().toArray()[3]);
        descendantsList.add((InnovatorMartian<Integer>) martian.getDescendants().toArray()[4]);
        descendantsList.add((InnovatorMartian<Integer>) martian.getDescendants().toArray()[5]);
        descendantsList.add((InnovatorMartian<Integer>) martian.getDescendants().toArray()[6]);
        descendantsList.add((InnovatorMartian<Integer>) martian.getDescendants().toArray()[7]);
        descendantsList.add((InnovatorMartian<Integer>) martian.getDescendants().toArray()[8]);
        //Получаем InnovatorMartian(Integer:9) и устанавливаем ему лист потомков (child = InnovatorMartian(Integer:9))
        InnovatorMartian<Integer> child = (InnovatorMartian<Integer>) martian.getChildren().toArray()[1];
        child.setDescendants(descendantsList);
        assertEquals(martian.getFamilyTree().toString(), readAllText("results\\setDescendants\\result1.txt"));
        //Добавляем в лист отца InnovatorMartian(Integer:9)
        descendantsList.add(martian);
        SetDescendantsException e = assertThrows(SetDescendantsException.class,
                () -> child.setDescendants(descendantsList));
        assertEquals(e.getMessage(), "Невозможно добавить своего предка в потомки");
        //Удаляем отца и добавляем в лист null
        descendantsList.remove(martian);
        descendantsList.add(null);
        e = assertThrows(SetDescendantsException.class, () -> child.setDescendants(descendantsList));
        assertEquals(e.getMessage(), "Был передан null");
        //Уберем null и добавим себя
        descendantsList.remove(null);
        descendantsList.add(child);
        e = assertThrows(SetDescendantsException.class, () -> child.setDescendants(descendantsList));
        assertEquals(e.getMessage(), "Невозможно добавить своего предка в потомки");
        //Передадим вместо коллекции null
        e = assertThrows(SetDescendantsException.class, () -> child.setDescendants(null));
        assertEquals(e.getMessage(), "Коллекция была null");
    }

    @Test
    void addChild1() {
        //Создаем нового марсианина и добавляем его в список детей марсианина
        InnovatorMartian<Integer> newMartian = new InnovatorMartian<>(100, null, new ArrayList<>());
        martian.addChild(newMartian);
        assertEquals(martian.getFamilyTree().toString(), readAllText("results\\addChild\\result1.txt"));
        assertTrue(martian.hasChild(newMartian)); //Проверяем связь martian и newMartian
        assertEquals(newMartian.getParent(), martian); //Проверяем связь martian и newMartian
        //Добавим новому марсианину ребенка и проверим как это отразится на итоговом дереве
        InnovatorMartian<Integer> newMartian2 = new InnovatorMartian<>(150, null, new ArrayList<>());
        newMartian.addChild(newMartian2);
        assertEquals(martian.getFamilyTree().toString(), readAllText("results\\addChild\\result2.txt"));
        assertTrue(newMartian.hasChild(newMartian2)); //Проверяем связь newMartian и newMartian2
        assertEquals(newMartian2.getParent(), newMartian); //Проверяем связь newMartian и newMartian2
        //Добавим newMartian ребенка newMartian2 еще раз
        assertFalse(newMartian.addChild(newMartian2));
    }

    @Test
    void addChild2() {
        //Добавим деду в качестве ребенка его внука
        InnovatorMartian<Integer> child = (InnovatorMartian<Integer>) martian.getChildren().toArray()[0];
        InnovatorMartian<Integer> grandchild = (InnovatorMartian<Integer>) child.getChildren().toArray()[0];
        martian.addChild(grandchild);
        assertEquals(martian.getFamilyTree().toString(), readAllText("results\\addChild\\result3.txt"));
        assertTrue(martian.hasChild(grandchild)); //Проверяем связь martian и grandchild
        assertEquals(grandchild.getParent(), martian); //Проверяем связь martian и grandchild
    }

    @Test
    void addChild3() {
        //Попробуем добавить каждому марсианину его потомка или его самого
        for (Martian<Integer> martian1: martian.getDescendants())
            for (Martian<Integer> ancestor : martian1.getAncestors(new ArrayList<>()))
                assertFalse(((InnovatorMartian<Integer>) martian1).addChild((InnovatorMartian<Integer>) ancestor));
    }

    @Test
    void deleteChild1() {
        //Удалим ребенка у martian
        InnovatorMartian<Integer> child = (InnovatorMartian<Integer>) martian.getChildren().toArray()[1];
        martian.deleteChild(child);
        assertEquals(martian.getFamilyTree().toString(), readAllText("results\\deleteChild\\result1.txt"));
        assertFalse(martian.hasChild(child)); //проверяем наличие ребенка у martian
        assertEquals(child.getParent(), null); //проверяем наличие родителя у child
    }

    @Test
    void deleteChild2() {
        //Попробуем удалить из списка детей несуществующего в семье марсианина
        InnovatorMartian<Integer> newMartian = new InnovatorMartian<>(100, null, new ArrayList<>());
        assertFalse(martian.deleteChild(newMartian));
    }

    @Test
    void deleteChild3() {
        //Пройдемся по потомкам детей (внуки, правнуки и т.д. martian) и попробуем удалить их из martian
        for (Martian<Integer> childCycle: martian.getChildren())
            for (Martian<Integer> grandchild: childCycle.getDescendants())
                assertFalse(martian.deleteChild((InnovatorMartian<Integer>) grandchild));
    }

    @Test
    void deleteChild4() {
        //Удалим всех детей
        for (Martian<Integer> childCycle: martian.getChildren())
            assertTrue(martian.deleteChild((InnovatorMartian<Integer>) childCycle));
        assertEquals(martian.getFamilyTree().toString(), readAllText("results\\deleteChild\\result2.txt"));
    }

    @Test
    void getValue() {
            int value = 110;
            InnovatorMartian<Integer> newMartian = new InnovatorMartian<>(value, null, new ArrayList<>());
            assertEquals(value, newMartian.getValue());
    }

    @Test
    void getParent() {
        InnovatorMartian<Integer> child = (InnovatorMartian<Integer>) martian.getChildren().toArray()[0];
        assertEquals(child.getParent(), martian);
    }

    @Test
    void getChildren() {
        //Создаем список марсиан и передаем их в качестве детей
        ArrayList<InnovatorMartian<Integer>> martians = new ArrayList<>();
        martians.add(new InnovatorMartian<>(101, null, new ArrayList<>()));
        martians.add(new InnovatorMartian<>(102, null, new ArrayList<>()));
        martians.add(new InnovatorMartian<>(103, null, new ArrayList<>()));
        martians.add(new InnovatorMartian<>(104, null, new ArrayList<>()));
        martians.add(new InnovatorMartian<>(105, null, new ArrayList<>()));
        //Создаем родителя этих детей
        InnovatorMartian<Integer> newMartian = new InnovatorMartian<Integer>(111, null, martians);
        //Сравниваем список детей и результат метода getChildren()
        assertEquals(martians, newMartian.getChildren());
    }

    @Test
    void getDescendants() {
        //Создаем список потомков
        ArrayList<InnovatorMartian<Integer>> martians = new ArrayList<>();
        martians.add(new InnovatorMartian<>(101, null, new ArrayList<>()));
        martians.add(new InnovatorMartian<>(102, martians.get(0), new ArrayList<>()));
        martians.add(new InnovatorMartian<>(103, null, new ArrayList<>()));
        martians.add(new InnovatorMartian<>(104, martians.get(0), new ArrayList<>()));
        martians.add(new InnovatorMartian<>(105, martians.get(0), new ArrayList<>()));
        martians.add(new InnovatorMartian<>(106, martians.get(1), new ArrayList<>()));
        martians.add(new InnovatorMartian<>(107, null, new ArrayList<>()));
        martians.add(new InnovatorMartian<>(108, martians.get(1), new ArrayList<>()));
        martians.add(new InnovatorMartian<>(109, martians.get(1), new ArrayList<>()));
        martians.add(new InnovatorMartian<>(110, martians.get(1), new ArrayList<>()));
        //Создаем список детей
        ArrayList<InnovatorMartian<Integer>> children = new ArrayList<>();
        children.add(martians.get(0));
        children.add(martians.get(2));
        children.add(martians.get(6));
        //Создаем предка созданных выше потомков
        InnovatorMartian<Integer> newMartian = new InnovatorMartian<Integer>(111, null, children);
        Collection<Martian<Integer>> descendants = newMartian.getDescendants();
        //Проверяем наличие всех потомков возвращенных методом getDescendants()
        for (InnovatorMartian<Integer> m: martians)
            assertTrue(descendants.contains(m));
    }

    @Test
    void getFamilyTree() {
        assertEquals(martian.getFamilyTree(), ft);
    }

}