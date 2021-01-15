package sample;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class ConservativeMartianTest {

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
    void getValue() {
        //Проверяем равенство значений value и getValue()
        int value = 110;
        InnovatorMartian<Integer> newMartian = new InnovatorMartian<>(value, null, new ArrayList<>());
        ConservativeMartian<Integer> conservativeMartian = new ConservativeMartian<>(newMartian);
        assertEquals(value, conservativeMartian.getValue());
    }

    @Test
    void getParent() {
        ConservativeMartian<Integer> conservativeMartian = new ConservativeMartian<>(martian);
        ConservativeMartian<Integer> child = (ConservativeMartian<Integer>) conservativeMartian.getChildren().toArray()[0];
        assertEquals(child.getParent(), conservativeMartian);
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
        //Создаем консерватора из новатора и получаем строковые представления его детей
        ConservativeMartian<Integer> conservativeMartian = new ConservativeMartian<>(newMartian);
        ArrayList<String> stringDescendants = new ArrayList<>();
        for (Martian<Integer> martian: conservativeMartian.getChildren())
            stringDescendants.add(martian.toString());
        //Сравниваем строковые представления детей со строковыми представлениями марсиан из списка
        for (InnovatorMartian<Integer> m: martians)
            assertTrue(stringDescendants.contains((new ConservativeMartian<Integer>(m)).toString()));
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
        //Создаем консерватора из новатора и получаем строковые представления его потомков
        ConservativeMartian<Integer> conservativeMartian = new ConservativeMartian<>(newMartian);
        ArrayList<String> stringDescendants = new ArrayList<>();
        //Выписываем строковые представления потомков
        for (Martian<Integer> martian: conservativeMartian.getDescendants())
            stringDescendants.add(martian.toString());
        //Сравниваем строковые представления потомков со строковыми представлениями марсиан из списка
        for (InnovatorMartian<Integer> m: martians)
            assertTrue(stringDescendants.contains((new ConservativeMartian<Integer>(m)).toString()));
    }
}