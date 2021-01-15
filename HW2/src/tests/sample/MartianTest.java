package sample;

import com.sun.jdi.DoubleValue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class MartianTest {

    private static String report1;
    private static String report2;
    private static String report3;
    private InnovatorMartian<Integer> martian1;
    private InnovatorMartian<String> martian2;
    private InnovatorMartian<Double> martian3;
    private FamilyTree ft1;
    private FamilyTree ft2;
    private FamilyTree ft3;

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
        report1 = readAllText("reports\\report1.txt");
        report2 = readAllText("reports\\report2.txt");
        report3 = readAllText("reports\\report3.txt");
    }

    @BeforeEach
    void setUp() {
        Martian.clearExistingValues(); //очищаем лист существуюших значений
    }

    /**
     * Проходимся по всем потомкам корня и проверяем, что они определяют
     * корень своего дерева верно (проверка новатора на основе report1.txt)
     */
    @Test
    void getFirstMartianInnovator1() {
        ft1 = FamilyTree.createTreeByReport(report1);
        martian1 = (InnovatorMartian<Integer>) ft1.getMartianNode();
        InnovatorMartian<Integer> martian = (InnovatorMartian<Integer>) martian1;
        for (Martian<Integer> descendant: martian.getDescendants())
            assertEquals(martian, descendant.getFirstMartian());
    }

    /**
     * Проходимся по всем потомкам корня и проверяем, что они определяют
     * корень своего дерева верно (проверка новатора на основе report2.txt)
     */
    @Test
    void getFirstMartianInnovator2() {
        ft2 = FamilyTree.createTreeByReport(report2);
        martian2 = (InnovatorMartian<String>) ft2.getMartianNode();
        InnovatorMartian<String> martian = (InnovatorMartian<String>) martian2;
        for (Martian<String> descendant: martian.getDescendants())
            assertEquals(martian, descendant.getFirstMartian());
    }

    /**
     * Проходимся по всем потомкам корня и проверяем, что они определяют
     * корень своего дерева верно (проверка новатора на основе report3.txt)
     */
    @Test
    void getFirstMartianInnovator3() {
        ft3 = FamilyTree.createTreeByReport(report3);
        martian3 = (InnovatorMartian<Double>) ft3.getMartianNode();
        InnovatorMartian<Double> martian = (InnovatorMartian<Double>) martian3;
        for (Martian<Double> descendant: martian.getDescendants())
            assertEquals(martian, descendant.getFirstMartian());
    }

    /**
     * Проходимся по всем потомкам корня и проверяем, что они определяют
     * корень своего дерева верно (проверка консерватора на основе report1.txt)
     */
    @Test
    void getFirstMartianConservative1() {
        ft1 = FamilyTree.createTreeByReport(report1);
        martian1 = (InnovatorMartian<Integer>) ft1.getMartianNode();
        ConservativeMartian<Integer> martian = new ConservativeMartian<>(martian1);
        for (Martian<Integer> descendant: martian.getDescendants())
            assertEquals(martian, descendant.getFirstMartian());
    }

    /**
     * Проходимся по всем потомкам корня и проверяем, что они определяют
     * корень своего дерева верно (проверка консерватора на основе report2.txt)
     */
    @Test
    void getFirstMartianConservative2() {
        ft2 = FamilyTree.createTreeByReport(report2);
        martian2 = (InnovatorMartian<String>) ft2.getMartianNode();
        ConservativeMartian<String> martian = new ConservativeMartian<>(martian2);
        for (Martian<String> descendant: martian.getDescendants())
            assertEquals(martian, descendant.getFirstMartian());
    }

    /**
     * Проходимся по всем потомкам корня и проверяем, что они определяют
     * корень своего дерева верно (проверка консерватора на основе report3.txt)
     */
    @Test
    void getFirstMartianConservative3() {
        ft3 = FamilyTree.createTreeByReport(report3);
        martian3 = (InnovatorMartian<Double>) ft3.getMartianNode();
        ConservativeMartian<Double> martian = new ConservativeMartian<>(martian3);
        for (Martian<Double> descendant: martian.getDescendants())
            assertEquals(martian, descendant.getFirstMartian());
    }

    /**
     * Проходимся по всем узлам кроме корня и пытаемся найти в детях узел
     * со значением одного из дочерних узлов
     * (проверка новатора на основе report1.txt, все значния узлов разные
     * для простоты проверки)
     */
    @Test
    void hasChildWithValueInnovator1() {
        //Ищем value ребенка
        ft1 = FamilyTree.createTreeByReport(report1);
        martian1 = (InnovatorMartian<Integer>) ft1.getMartianNode();
        for (Martian<Integer> descendant: martian1.getDescendants())
            for (Martian<Integer> child: descendant.getChildren())
                assertTrue(descendant.hasChildWithValue(child.getValue()));
    }

    /**
     * Проходимся по всем узлам кроме корня и пытаемся найти в детях узел
     * со значением одного из дочерних узлов
     * (проверка новатора на основе report2.txt, все значния узлов разные
     * для простоты проверки)
     */
    @Test
    void hasChildWithValueInnovator2() {
        //Ищем value ребенка
        ft2 = FamilyTree.createTreeByReport(report2);
        martian2 = (InnovatorMartian<String>) ft2.getMartianNode();
        for (Martian<String> descendant: martian2.getDescendants())
            for (Martian<String> child: descendant.getChildren())
                assertTrue(descendant.hasChildWithValue(child.getValue()));
    }

    /**
     * Проходимся по всем узлам кроме корня и пытаемся найти в детях узел
     * со значением одного из дочерних узлов
     * (проверка новатора на основе report3.txt, все значния узлов разные
     * для простоты проверки)
     */
    @Test
    void hasChildWithValueInnovator3() {
        //Ищем value ребенка
        ft3 = FamilyTree.createTreeByReport(report3);
        martian3 = (InnovatorMartian<Double>) ft3.getMartianNode();
        for (Martian<Double> descendant: martian3.getDescendants())
            for (Martian<Double> child: descendant.getChildren())
                assertTrue(descendant.hasChildWithValue(child.getValue()));
    }

    /**
     * Проходимся по всем узлам кроме корня и пытаемся найти в детях узел
     * со значением другого узла, который не является ребенком
     * (проверка новатора на основе report1.txt, все значния узлов разные
     * для простоты проверки)
     */
    @Test
    void hasChildWithValueInnovator4() {
        //Пытаемся найти value не ребенка в списке
        ft1 = FamilyTree.createTreeByReport(report1);
        martian1 = (InnovatorMartian<Integer>) ft1.getMartianNode();
        for (Martian<Integer> descendant: martian1.getDescendants()) {
            Collection<Martian<Integer>> withoutChildren = descendant.getDescendants();
            withoutChildren.removeAll(descendant.getChildren());
            for (Martian<Integer> notChild : withoutChildren)
                assertFalse(descendant.hasChildWithValue(notChild.getValue()));
        }
    }

    /**
     * Проходимся по всем узлам кроме корня и пытаемся найти в детях узел
     * со значением другого узла, который не является ребенком
     * (проверка новатора на основе report2.txt, все значния узлов разные
     * для простоты проверки)
     */
    @Test
    void hasChildWithValueInnovator5() {
        //Пытаемся найти value не ребенка в списке
        ft2 = FamilyTree.createTreeByReport(report2);
        martian2 = (InnovatorMartian<String>) ft2.getMartianNode();
        for (Martian<String> descendant: martian2.getDescendants()) {
            Collection<Martian<String>> withoutChildren = descendant.getDescendants();
            withoutChildren.removeAll(descendant.getChildren());
            for (Martian<String> notChild : withoutChildren)
                assertFalse(descendant.hasChildWithValue(notChild.getValue()));
        }
    }

    /**
     * Проходимся по всем узлам кроме корня и пытаемся найти в детях узел
     * со значением другого узла, который не является ребенком
     * (проверка новатора на основе report3.txt, все значния узлов разные
     * для простоты проверки)
     */
    @Test
    void hasChildWithValueInnovator6() {
        //Пытаемся найти value не ребенка в списке
        ft3 = FamilyTree.createTreeByReport(report3);
        martian3 = (InnovatorMartian<Double>) ft3.getMartianNode();
        for (Martian<Double> descendant: martian3.getDescendants()) {
            Collection<Martian<Double>> withoutChildren = descendant.getDescendants();
            withoutChildren.removeAll(descendant.getChildren());
            for (Martian<Double> notChild : withoutChildren)
                assertFalse(descendant.hasChildWithValue(notChild.getValue()));
        }
    }

    /**
     * Проходимся по всем узлам кроме корня и пытаемся найти в детях узел
     * со значением одного из дочерних узлов
     * (проверка консерватора на основе report1.txt, все значния узлов разные
     * для простоты проверки)
     */
    @Test
    void hasChildWithValueConservative1() {
        //Ищем value ребенка
        ft1 = FamilyTree.createTreeByReport(report1);
        martian1 = (InnovatorMartian<Integer>) ft1.getMartianNode();
        ConservativeMartian<Integer> martian = new ConservativeMartian<>(martian1);
        for (Martian<Integer> descendant: martian.getDescendants())
            for (Martian<Integer> child: descendant.getChildren())
                assertTrue(descendant.hasChildWithValue(child.getValue()));
    }

    /**
     * Проходимся по всем узлам кроме корня и пытаемся найти в детях узел
     * со значением одного из дочерних узлов
     * (проверка консерватора на основе report2.txt, все значния узлов разные
     * для простоты проверки)
     */
    @Test
    void hasChildWithValueConservative2() {
        //Ищем value ребенка
        ft2 = FamilyTree.createTreeByReport(report2);
        martian2 = (InnovatorMartian<String>) ft2.getMartianNode();
        ConservativeMartian<String> martian = new ConservativeMartian<>(martian2);
        for (Martian<String> descendant: martian.getDescendants())
            for (Martian<String> child: descendant.getChildren())
                assertTrue(descendant.hasChildWithValue(child.getValue()));
    }

    /**
     * Проходимся по всем узлам кроме корня и пытаемся найти в детях узел
     * со значением одного из дочерних узлов
     * (проверка консерватора на основе report3.txt, все значния узлов разные
     * для простоты проверки)
     */
    @Test
    void hasChildWithValueConservative3() {
        //Ищем value ребенка
        ft3 = FamilyTree.createTreeByReport(report3);
        martian3 = (InnovatorMartian<Double>) ft3.getMartianNode();
        ConservativeMartian<Double> martian = new ConservativeMartian<>(martian3);
        for (Martian<Double> descendant: martian.getDescendants())
            for (Martian<Double> child: descendant.getChildren())
                assertTrue(descendant.hasChildWithValue(child.getValue()));
    }

    /**
     * Проходимся по всем узлам кроме корня и пытаемся найти в детях узел
     * со значением другого узла, который не является ребенком
     * (проверка консерватора на основе report1.txt, все значния узлов разные
     * для простоты проверки)
     */
    @Test
    void hasChildWithValueConservative4() {
        //Пытаемся найти value не ребенка в списке
        ft1 = FamilyTree.createTreeByReport(report1);
        martian1 = (InnovatorMartian<Integer>) ft1.getMartianNode();
        ConservativeMartian<Integer> martian = new ConservativeMartian<Integer>(martian1);
        for (Martian<Integer> descendant: martian.getDescendants()) {
            Collection<Martian<Integer>> withoutChildren = descendant.getDescendants();
            withoutChildren.removeAll(descendant.getChildren());
            for (Martian<Integer> notChild : withoutChildren)
                assertFalse(descendant.hasChildWithValue(notChild.getValue()));
        }
    }

    /**
     * Проходимся по всем узлам кроме корня и пытаемся найти в детях узел
     * со значением другого узла, который не является ребенком
     * (проверка консерватора на основе report2.txt, все значния узлов разные
     * для простоты проверки)
     */
    @Test
    void hasChildWithValueConservative5() {
        //Пытаемся найти value не ребенка в списке
        ft2 = FamilyTree.createTreeByReport(report2);
        martian2 = (InnovatorMartian<String>) ft2.getMartianNode();
        ConservativeMartian<String> martian = new ConservativeMartian<>(martian2);
        for (Martian<String> descendant: martian.getDescendants()) {
            Collection<Martian<String>> withoutChildren = descendant.getDescendants();
            withoutChildren.removeAll(descendant.getChildren());
            for (Martian<String> notChild : withoutChildren)
                assertFalse(descendant.hasChildWithValue(notChild.getValue()));
        }
    }

    /**
     * Проходимся по всем узлам кроме корня и пытаемся найти в детях узел
     * со значением другого узла, который не является ребенком
     * (проверка консерватора на основе report3.txt, все значния узлов разные
     * для простоты проверки)
     */
    @Test
    void hasChildWithValueConservative6() {
        //Пытаемся найти value не ребенка в списке
        ft3 = FamilyTree.createTreeByReport(report3);
        martian3 = (InnovatorMartian<Double>) ft3.getMartianNode();
        ConservativeMartian<Double> martian = new ConservativeMartian<>(martian3);
        for (Martian<Double> descendant: martian.getDescendants()) {
            Collection<Martian<Double>> withoutChildren = descendant.getDescendants();
            withoutChildren.removeAll(descendant.getChildren());
            for (Martian<Double> notChild : withoutChildren)
                assertFalse(descendant.hasChildWithValue(notChild.getValue()));
        }
    }

    /**
     * Проходится по всем узлам кроме корня и пытается найти в списке детей
     * узел, который есть в этом списке детей (проверка новатора на основе
     * report1.txt)
     */
    @Test
    void hasChildInnovator1() {
        //Ищем ребенка
        ft1 = FamilyTree.createTreeByReport(report1);
        martian1 = (InnovatorMartian<Integer>) ft1.getMartianNode();
        for (Martian<Integer> child: martian1.getChildren())
            assertTrue(martian1.hasChild(child));
    }

    /**
     * Проходится по всем узлам кроме корня и пытается найти в списке детей
     * узел, который есть в этом списке детей (проверка новатора на основе
     * report2.txt)
     */
    @Test
    void hasChildInnovator2() {
        //Ищем ребенка
        ft2 = FamilyTree.createTreeByReport(report2);
        martian2 = (InnovatorMartian<String>) ft2.getMartianNode();
        for (Martian<String> child: martian2.getChildren())
            assertTrue(martian2.hasChild(child));
    }

    /**
     * Проходится по всем узлам кроме корня и пытается найти в списке детей
     * узел, который есть в этом списке детей (проверка новатора на основе
     * report3.txt)
     */
    @Test
    void hasChildInnovator3() {
        //Ищем ребенка
        ft3 = FamilyTree.createTreeByReport(report3);
        martian3 = (InnovatorMartian<Double>) ft3.getMartianNode();
        for (Martian<Double> child: martian3.getChildren())
            assertTrue(martian3.hasChild(child));
    }

    /**
     * Проходится по всем узлам кроме корня и пытается найти в списке детей
     * узел, которого нет в этом списке детей (проверка новатора на основе
     * report1.txt)
     */
    @Test
    void hasChildInnovator4() {
        //Пытаемся найти value не ребенка в списке
        ft1 = FamilyTree.createTreeByReport(report1);
        martian1 = (InnovatorMartian<Integer>) ft1.getMartianNode();
        Collection<Martian<Integer>> withoutChildren = martian1.getDescendants();
        withoutChildren.removeAll(martian1.getChildren());
        for (Martian<Integer> notChild: withoutChildren)
            assertFalse(martian1.hasChild(notChild));
    }

    /**
     * Проходится по всем узлам кроме корня и пытается найти в списке детей
     * узел, которого нет в этом списке детей (проверка новатора на основе
     * report2.txt)
     */
    @Test
    void hasChildInnovator5() {
        //Пытаемся найти value не ребенка в списке
        ft2 = FamilyTree.createTreeByReport(report2);
        martian2 = (InnovatorMartian<String>) ft2.getMartianNode();
        Collection<Martian<String>> withoutChildren = martian2.getDescendants();
        withoutChildren.removeAll(martian2.getChildren());
        for (Martian<String> notChild: withoutChildren)
            assertFalse(martian2.hasChild(notChild));
    }

    /**
     * Проходится по всем узлам кроме корня и пытается найти в списке детей
     * узел, которого нет в этом списке детей (проверка новатора на основе
     * report3.txt)
     */
    @Test
    void hasChildInnovator6() {
        //Пытаемся найти value не ребенка в списке
        ft3 = FamilyTree.createTreeByReport(report3);
        martian3 = (InnovatorMartian<Double>) ft3.getMartianNode();
        Collection<Martian<Double>> withoutChildren = martian3.getDescendants();
        withoutChildren.removeAll(martian3.getChildren());
        for (Martian<Double> notChild: withoutChildren)
            assertFalse(martian3.hasChild(notChild));
    }

    /**
     * Проходится по всем узлам кроме корня и пытается найти в списке детей
     * узел, который есть в этом списке детей (проверка консерватора на основе
     * report1.txt)
     */
    @Test
    void hasChildConservative1() {
        //Ищем value ребенка
        ft1 = FamilyTree.createTreeByReport(report1);
        martian1 = (InnovatorMartian<Integer>) ft1.getMartianNode();
        ConservativeMartian<Integer> martian = new ConservativeMartian<>(martian1);
        for (Martian<Integer> child: martian.getChildren())
            assertTrue(martian.hasChild(child));
    }

    /**
     * Проходится по всем узлам кроме корня и пытается найти в списке детей
     * узел, который есть в этом списке детей (проверка консерватора на основе
     * report2.txt)
     */
    @Test
    void hasChildConservative2() {
        //Ищем value ребенка
        ft2 = FamilyTree.createTreeByReport(report2);
        martian2 = (InnovatorMartian<String>) ft2.getMartianNode();
        ConservativeMartian<String> martian = new ConservativeMartian<>(martian2);
        for (Martian<String> child: martian.getChildren())
            assertTrue(martian.hasChild(child));
    }

    /**
     * Проходится по всем узлам кроме корня и пытается найти в списке детей
     * узел, который есть в этом списке детей (проверка консерватора на основе
     * report3.txt)
     */
    @Test
    void hasChildConservative3() {
        //Ищем value ребенка
        ft3 = FamilyTree.createTreeByReport(report3);
        martian3 = (InnovatorMartian<Double>) ft3.getMartianNode();
        ConservativeMartian<Double> martian = new ConservativeMartian<>(martian3);
        for (Martian<Double> child: martian.getChildren())
            assertTrue(martian.hasChild(child));
    }

    /**
     * Проходится по всем узлам кроме корня и пытается найти в списке детей
     * узел, которого нет в этом списке детей (проверка консерватора на основе
     * report1.txt)
     */
    @Test
    void hasChildConservative4() {
        //Пытаемся найти value не ребенка в списке
        ft1 = FamilyTree.createTreeByReport(report1);
        martian1 = (InnovatorMartian<Integer>) ft1.getMartianNode();
        ConservativeMartian<Integer> martian = new ConservativeMartian<Integer>(martian1);
        Collection<Martian<Integer>> withoutChildren = martian.getDescendants();
        boolean t = withoutChildren.removeAll(martian.getChildren());
        for (Martian<Integer> notChild: withoutChildren)
            assertFalse(martian.hasChild(notChild));
    }

    /**
     * Проходится по всем узлам кроме корня и пытается найти в списке детей
     * узел, которого нет в этом списке детей (проверка консерватора на основе
     * report2.txt)
     */
    @Test
    void hasChildConservative5() {
        //Пытаемся найти value не ребенка в списке
        ft2 = FamilyTree.createTreeByReport(report2);
        martian2 = (InnovatorMartian<String>) ft2.getMartianNode();
        ConservativeMartian<String> martian = new ConservativeMartian<>(martian2);
        Collection<Martian<String>> withoutChildren = martian.getDescendants();
        withoutChildren.removeAll(martian.getChildren());
        for (Martian<String> notChild: withoutChildren)
            assertFalse(martian.hasChild(notChild));
    }

    /**
     * Проходится по всем узлам кроме корня и пытается найти в списке детей
     * узел, которого нет в этом списке детей (проверка консерватора на основе
     * report3.txt)
     */
    @Test
    void hasChildConservative6() {
        //Пытаемся найти value не ребенка в списке
        ft3 = FamilyTree.createTreeByReport(report3);
        martian3 = (InnovatorMartian<Double>) ft3.getMartianNode();
        ConservativeMartian<Double> martian = new ConservativeMartian<>(martian3);
        Collection<Martian<Double>> withoutChildren = martian.getDescendants();
        withoutChildren.removeAll(martian.getChildren());
        for (Martian<Double> notChild: withoutChildren)
            assertFalse(martian.hasChild(notChild));
    }

    /**
     * Проходится по всем узлам кроме корня и проверяет наличие
     * значения узлов потомков в списке потомков (проверка новатора
     * на основе report1.txt, все значния узлов разные для простоты проверки)
     */
    @Test
    void hasDescendantWithValueInnovator1() {
        //Ищем value потомка
        ft1 = FamilyTree.createTreeByReport(report1);
        martian1 = (InnovatorMartian<Integer>) ft1.getMartianNode();
        for (Martian<Integer> descendant: martian1.getDescendants())
            for (Martian<Integer> descendant2: descendant.getDescendants())
                assertTrue(descendant.hasDescendantWithValue(descendant2.getValue()));
    }

    /**
     * Проходится по всем узлам кроме корня и проверяет наличие
     * значения узлов потомков в списке потомков (проверка новатора
     * на основе report2.txt, все значния узлов разные для простоты проверки)
     */
    @Test
    void hasDescendantWithValueInnovator2() {
        //Ищем value потомка
        ft2 = FamilyTree.createTreeByReport(report2);
        martian2 = (InnovatorMartian<String>) ft2.getMartianNode();
        for (Martian<String> descendant: martian2.getDescendants())
            for (Martian<String> descendant2: descendant.getDescendants())
                assertTrue(descendant.hasDescendantWithValue(descendant2.getValue()));
    }

    /**
     * Проходится по всем узлам кроме корня и проверяет наличие
     * значения узлов потомков в списке потомков (проверка новатора
     * на основе report3.txt, все значния узлов разные для простоты проверки)
     */
    @Test
    void hasDescendantWithValueInnovator3() {
        //Ищем value потомка
        ft3 = FamilyTree.createTreeByReport(report3);
        martian3 = (InnovatorMartian<Double>) ft3.getMartianNode();
        for (Martian<Double> descendant: martian3.getDescendants())
            for (Martian<Double> descendant2: descendant.getDescendants())
                assertTrue(descendant.hasDescendantWithValue(descendant2.getValue()));
    }

    /**
     * Проходится по всем узлам без потомков и проверяет наличие значения
     * предков (проверка новатора на основе report1.txt, все значния
     * узлов разные для простоты проверки)
     */
    @Test
    void hasDescendantWithValueInnovator4() {
        //Ищем value не потомка
        ft1 = FamilyTree.createTreeByReport(report1);
        martian1 = (InnovatorMartian<Integer>) ft1.getMartianNode();
        ArrayList<Martian<Integer>> lastDescendants = new ArrayList<>();
        for (Martian<Integer> descendant: martian1.getDescendants())
            if (descendant.getChildren().size() == 0)
                lastDescendants.add(descendant);
        for (Martian<Integer> lastDescendant: lastDescendants) {
            ArrayList<Martian<Integer>> ancestors = new ArrayList<>();
            lastDescendant.getAncestors(ancestors);
            for (Martian<Integer> ancestor: ancestors)
                assertFalse(lastDescendant.hasDescendantWithValue(ancestor.getValue()));
        }
    }

    /**
     * Проходится по всем узлам без потомков и проверяет наличие значения
     * предков (проверка новатора на основе report2.txt, все значния
     * узлов разные для простоты проверки)
     */
    @Test
    void hasDescendantWithValueInnovator5() {
        //Ищем value не потомка
        ft2 = FamilyTree.createTreeByReport(report2);
        martian2 = (InnovatorMartian<String>) ft2.getMartianNode();
        ArrayList<Martian<String>> lastDescendants = new ArrayList<>();
        for (Martian<String> descendant: martian2.getDescendants())
            if (descendant.getChildren().size() == 0)
                lastDescendants.add(descendant);
        for (Martian<String> lastDescendant: lastDescendants) {
            ArrayList<Martian<String>> ancestors = new ArrayList<>();
            lastDescendant.getAncestors(ancestors);
            for (Martian<String> ancestor: ancestors)
                assertFalse(lastDescendant.hasDescendantWithValue(ancestor.getValue()));
        }
    }

    /**
     * Проходится по всем узлам без потомков и проверяет наличие значения
     * предков (проверка новатора на основе report3.txt, все значния
     * узлов разные для простоты проверки)
     */
    @Test
    void hasDescendantWithValueInnovator6() {
        //Ищем value не потомка
        ft3 = FamilyTree.createTreeByReport(report3);
        martian3 = (InnovatorMartian<Double>) ft3.getMartianNode();
        ArrayList<Martian<Double>> lastDescendants = new ArrayList<>();
        for (Martian<Double> descendant: martian3.getDescendants())
            if (descendant.getChildren().size() == 0)
                lastDescendants.add(descendant);
        for (Martian<Double> lastDescendant: lastDescendants) {
            ArrayList<Martian<Double>> ancestors = new ArrayList<>();
            lastDescendant.getAncestors(ancestors);
            for (Martian<Double> ancestor: ancestors)
                assertFalse(lastDescendant.hasDescendantWithValue(ancestor.getValue()));
        }
    }

    /**
     * Проходится по всем узлам кроме корня и проверяет наличие
     * значения узлов потомков в списке потомков (проверка консерватора
     * на основе report1.txt, все значния узлов разные для простоты проверки)
     */
    @Test
    void hasDescendantWithValueConservative1() {
        //Ищем value потомка
        ft1 = FamilyTree.createTreeByReport(report1);
        martian1 = (InnovatorMartian<Integer>) ft1.getMartianNode();
        ConservativeMartian<Integer> martian = new ConservativeMartian<>(martian1);
        for (Martian<Integer> descendant: martian.getDescendants())
            for (Martian<Integer> descendant2: descendant.getDescendants())
                assertTrue(descendant.hasDescendantWithValue(descendant2.getValue()));
    }

    /**
     * Проходится по всем узлам кроме корня и проверяет наличие
     * значения узлов потомков в списке потомков (проверка консерватора
     * на основе report2.txt, все значния узлов разные для простоты проверки)
     */
    @Test
    void hasDescendantWithValueConservative2() {
        //Ищем value потомка
        ft2 = FamilyTree.createTreeByReport(report2);
        martian2 = (InnovatorMartian<String>) ft2.getMartianNode();
        ConservativeMartian<String> martian = new ConservativeMartian<>(martian2);
        for (Martian<String> descendant: martian.getDescendants())
            for (Martian<String> descendant2: descendant.getDescendants())
                assertTrue(descendant.hasDescendantWithValue(descendant2.getValue()));
    }

    /**
     * Проходится по всем узлам кроме корня и проверяет наличие
     * значения узлов потомков в списке потомков (проверка консерватора
     * на основе report3.txt, все значния узлов разные для простоты проверки)
     */
    @Test
    void hasDescendantWithValueConservative3() {
        //Ищем value потомка
        ft3 = FamilyTree.createTreeByReport(report3);
        martian3 = (InnovatorMartian<Double>) ft3.getMartianNode();
        ConservativeMartian<Double> martian = new ConservativeMartian<>(martian3);
        for (Martian<Double> descendant: martian.getDescendants())
            for (Martian<Double> descendant2: descendant.getDescendants())
                assertTrue(descendant.hasDescendantWithValue(descendant2.getValue()));
    }

    /**
     * Проходится по всем узлам без потомков и проверяет наличие значения
     * предков (проверка консерватора на основе report1.txt, все значния
     * узлов разные для простоты проверки)
     */
    @Test
    void hasDescendantWithValueConservative4() {
        //Ищем value не потомка
        ft1 = FamilyTree.createTreeByReport(report1);
        martian1 = (InnovatorMartian<Integer>) ft1.getMartianNode();
        ConservativeMartian<Integer> martian = new ConservativeMartian<Integer>(martian1);
        ArrayList<Martian<Integer>> lastDescendants = new ArrayList<>();
        for (Martian<Integer> descendant: martian.getDescendants())
            if (descendant.getChildren().size() == 0)
                lastDescendants.add(descendant);
        for (Martian<Integer> lastDescendant: lastDescendants) {
            ArrayList<Martian<Integer>> ancestors = new ArrayList<>();
            lastDescendant.getAncestors(ancestors);
            for (Martian<Integer> ancestor: ancestors)
                assertFalse(lastDescendant.hasDescendantWithValue(ancestor.getValue()));
        }
    }

    /**
     * Проходится по всем узлам без потомков и проверяет наличие значения
     * предков (проверка консерватора на основе report2.txt, все значния
     * узлов разные для простоты проверки)
     */
    @Test
    void hasDescendantWithValueConservative5() {
        //Ищем value не потомка
        ft2 = FamilyTree.createTreeByReport(report2);
        martian2 = (InnovatorMartian<String>) ft2.getMartianNode();
        ConservativeMartian<String> martian = new ConservativeMartian<>(martian2);
        ArrayList<Martian<String>> lastDescendants = new ArrayList<>();
        for (Martian<String> descendant: martian.getDescendants())
            if (descendant.getChildren().size() == 0)
                lastDescendants.add(descendant);
        for (Martian<String> lastDescendant: lastDescendants) {
            ArrayList<Martian<String>> ancestors = new ArrayList<>();
            lastDescendant.getAncestors(ancestors);
            for (Martian<String> ancestor: ancestors)
                assertFalse(lastDescendant.hasDescendantWithValue(ancestor.getValue()));
        }
    }

    /**
     * Проходится по всем узлам без потомков и проверяет наличие значения
     * предков (проверка консерватора на основе report3.txt, все значния
     * узлов разные для простоты проверки)
     */
    @Test
    void hasDescendantWithValueConservative6() {
        //Ищем value не потомка
        ft3 = FamilyTree.createTreeByReport(report3);
        martian3 = (InnovatorMartian<Double>) ft3.getMartianNode();
        ConservativeMartian<Double> martian = new ConservativeMartian<>(martian3);
        ArrayList<Martian<Double>> lastDescendants = new ArrayList<>();
        for (Martian<Double> descendant: martian.getDescendants())
            if (descendant.getChildren().size() == 0)
                lastDescendants.add(descendant);
        for (Martian<Double> lastDescendant: lastDescendants) {
            ArrayList<Martian<Double>> ancestors = new ArrayList<>();
            lastDescendant.getAncestors(ancestors);
            for (Martian<Double> ancestor: ancestors)
                assertFalse(lastDescendant.hasDescendantWithValue(ancestor.getValue()));
        }
    }

    /**
     * Проходится по всем узлам кроме корня и проверяет наличие
     * узлов потомков в списке потомков (проверка новатора
     * на основе report1.txt)
     */
    @Test
    void hasDescendantInnovator1() {
        //Ищем потомка
        ft1 = FamilyTree.createTreeByReport(report1);
        martian1 = (InnovatorMartian<Integer>) ft1.getMartianNode();
        for (Martian<Integer> descendant: martian1.getDescendants())
            for (Martian<Integer> descendant2: descendant.getDescendants())
                assertTrue(descendant.hasDescendant(descendant2));
    }

    /**
     * Проходится по всем узлам кроме корня и проверяет наличие
     * узлов потомков в списке потомков (проверка новатора
     * на основе report2.txt)
     */
    @Test
    void hasDescendantInnovator2() {
        //Ищем потомка
        ft2 = FamilyTree.createTreeByReport(report2);
        martian2 = (InnovatorMartian<String>) ft2.getMartianNode();
        for (Martian<String> descendant: martian2.getDescendants())
            for (Martian<String> descendant2: descendant.getDescendants())
                assertTrue(descendant.hasDescendant(descendant2));
    }

    /**
     * Проходится по всем узлам кроме корня и проверяет наличие
     * узлов потомков в списке потомков (проверка новатора
     * на основе report3.txt)
     */
    @Test
    void hasDescendantInnovator3() {
        //Ищем потомка
        ft3 = FamilyTree.createTreeByReport(report3);
        martian3 = (InnovatorMartian<Double>) ft3.getMartianNode();
        for (Martian<Double> descendant: martian3.getDescendants())
            for (Martian<Double> descendant2: descendant.getDescendants())
                assertTrue(descendant.hasDescendant(descendant2));
    }

    /**
     * Проходится по всем узлам без потомков и проверяет наличие
     * предков (проверка новатора на основе report1.txt)
     */
    @Test
    void hasDescendantInnovator4() {
        //Ищем не потомка
        ft1 = FamilyTree.createTreeByReport(report1);
        martian1 = (InnovatorMartian<Integer>) ft1.getMartianNode();
        ArrayList<Martian<Integer>> lastDescendants = new ArrayList<>();
        for (Martian<Integer> descendant: martian1.getDescendants())
            if (descendant.getChildren().size() == 0)
                lastDescendants.add(descendant);
        for (Martian<Integer> lastDescendant: lastDescendants) {
            ArrayList<Martian<Integer>> ancestors = new ArrayList<>();
            lastDescendant.getAncestors(ancestors);
            for (Martian<Integer> ancestor: ancestors)
                assertFalse(lastDescendant.hasDescendant(ancestor));
        }
    }

    /**
     * Проходится по всем узлам без потомков и проверяет наличие
     * предков (проверка новатора на основе report2.txt)
     */
    @Test
    void hasDescendantInnovator5() {
        //Ищем не потомка
        ft2 = FamilyTree.createTreeByReport(report2);
        martian2 = (InnovatorMartian<String>) ft2.getMartianNode();
        ArrayList<Martian<String>> lastDescendants = new ArrayList<>();
        for (Martian<String> descendant: martian2.getDescendants())
            if (descendant.getChildren().size() == 0)
                lastDescendants.add(descendant);
        for (Martian<String> lastDescendant: lastDescendants) {
            ArrayList<Martian<String>> ancestors = new ArrayList<>();
            lastDescendant.getAncestors(ancestors);
            for (Martian<String> ancestor: ancestors)
                assertFalse(lastDescendant.hasDescendant(ancestor));
        }
    }

    /**
     * Проходится по всем узлам без потомков и проверяет наличие
     * предков (проверка новатора на основе report3.txt)
     */
    @Test
    void hasDescendantInnovator6() {
        //Ищем не потомка
        ft3 = FamilyTree.createTreeByReport(report3);
        martian3 = (InnovatorMartian<Double>) ft3.getMartianNode();
        ArrayList<Martian<Double>> lastDescendants = new ArrayList<>();
        for (Martian<Double> descendant: martian3.getDescendants())
            if (descendant.getChildren().size() == 0)
                lastDescendants.add(descendant);
        for (Martian<Double> lastDescendant: lastDescendants) {
            ArrayList<Martian<Double>> ancestors = new ArrayList<>();
            lastDescendant.getAncestors(ancestors);
            for (Martian<Double> ancestor: ancestors)
                assertFalse(lastDescendant.hasDescendant(ancestor));
        }
    }

    /**
     * Проходится по всем узлам кроме корня и проверяет наличие
     * узлов потомков в списке потомков (проверка консерватора
     * на основе report1.txt)
     */
    @Test
    void hasDescendantConservative1() {
        //Ищем потомка
        ft1 = FamilyTree.createTreeByReport(report1);
        martian1 = (InnovatorMartian<Integer>) ft1.getMartianNode();
        ConservativeMartian<Integer> martian = new ConservativeMartian<>(martian1);
        for (Martian<Integer> descendant: martian.getDescendants())
            for (Martian<Integer> descendant2: descendant.getDescendants())
                assertTrue(descendant.hasDescendant(descendant2));
    }

    /**
     * Проходится по всем узлам кроме корня и проверяет наличие
     * узлов потомков в списке потомков (проверка консерватора
     * на основе report2.txt)
     */
    @Test
    void hasDescendantConservative2() {
        //Ищем потомка
        ft2 = FamilyTree.createTreeByReport(report2);
        martian2 = (InnovatorMartian<String>) ft2.getMartianNode();
        ConservativeMartian<String> martian = new ConservativeMartian<>(martian2);
        for (Martian<String> descendant: martian.getDescendants())
            for (Martian<String> descendant2: descendant.getDescendants())
                assertTrue(descendant.hasDescendant(descendant2));
    }

    /**
     * Проходится по всем узлам кроме корня и проверяет наличие
     * узлов потомков в списке потомков (проверка консерватора
     * на основе report3.txt)
     */
    @Test
    void hasDescendantConservative3() {
        //Ищем потомка
        ft3 = FamilyTree.createTreeByReport(report3);
        martian3 = (InnovatorMartian<Double>) ft3.getMartianNode();
        ConservativeMartian<Double> martian = new ConservativeMartian<>(martian3);
        for (Martian<Double> descendant: martian.getDescendants())
            for (Martian<Double> descendant2: descendant.getDescendants())
                assertTrue(descendant.hasDescendant(descendant2));
    }

    /**
     * Проходится по всем узлам без потомков и проверяет наличие
     * предков (проверка консерватора на основе report1.txt)
     */
    @Test
    void hasDescendantConservative4() {
        //Ищем не потомка
        ft1 = FamilyTree.createTreeByReport(report1);
        martian1 = (InnovatorMartian<Integer>) ft1.getMartianNode();
        ConservativeMartian<Integer> martian = new ConservativeMartian<Integer>(martian1);
        ArrayList<Martian<Integer>> lastDescendants = new ArrayList<>();
        for (Martian<Integer> descendant: martian.getDescendants())
            if (descendant.getChildren().size() == 0)
                lastDescendants.add(descendant);
        for (Martian<Integer> lastDescendant: lastDescendants) {
            ArrayList<Martian<Integer>> ancestors = new ArrayList<>();
            lastDescendant.getAncestors(ancestors);
            for (Martian<Integer> ancestor: ancestors)
                assertFalse(lastDescendant.hasDescendant(ancestor));
        }
    }

    /**
     * Проходится по всем узлам без потомков и проверяет наличие
     * предков (проверка консерватора на основе report2.txt)
     */
    @Test
    void hasDescendantConservative5() {
        //Ищем не потомка
        ft2 = FamilyTree.createTreeByReport(report2);
        martian2 = (InnovatorMartian<String>) ft2.getMartianNode();
        ConservativeMartian<String> martian = new ConservativeMartian<>(martian2);
        ArrayList<Martian<String>> lastDescendants = new ArrayList<>();
        for (Martian<String> descendant: martian.getDescendants())
            if (descendant.getChildren().size() == 0)
                lastDescendants.add(descendant);
        for (Martian<String> lastDescendant: lastDescendants) {
            ArrayList<Martian<String>> ancestors = new ArrayList<>();
            lastDescendant.getAncestors(ancestors);
            for (Martian<String> ancestor: ancestors)
                assertFalse(lastDescendant.hasDescendant(ancestor));
        }
    }

    /**
     * Проходится по всем узлам без потомков и проверяет наличие
     * предков (проверка консерватора на основе report3.txt)
     */
    @Test
    void hasDescendantConservative6() {
        //Ищем не потомка
        ft3 = FamilyTree.createTreeByReport(report3);
        martian3 = (InnovatorMartian<Double>) ft3.getMartianNode();
        ConservativeMartian<Double> martian = new ConservativeMartian<>(martian3);
        ArrayList<Martian<Double>> lastDescendants = new ArrayList<>();
        for (Martian<Double> descendant: martian.getDescendants())
            if (descendant.getChildren().size() == 0)
                lastDescendants.add(descendant);
        for (Martian<Double> lastDescendant: lastDescendants) {
            ArrayList<Martian<Double>> ancestors = new ArrayList<>();
            lastDescendant.getAncestors(ancestors);
            for (Martian<Double> ancestor: ancestors)
                assertFalse(lastDescendant.hasDescendant(ancestor));
        }
    }

    /**
     * Проходимся по всем узлам кроме корня, получаем его предков
     * и проверяем, что из предков выстраивается цепочка (каждый из них
     * является любому другому сыном-отцом, внуком-дедом, правнуком-прадедом,
     * ...) (проверка новатора на основе report1.txt)
     */
    @Test
    void getAncestorsInnovator1() {
        //Проверяем, что каждый предок есть в списке детей следующего предка
        ft1 = FamilyTree.createTreeByReport(report1);
        martian1 = (InnovatorMartian<Integer>) ft1.getMartianNode();
        for (Martian<Integer> descendant: martian1.getDescendants()) {
            ArrayList<Martian<Integer>> ancestors = new ArrayList<>();
            descendant.getAncestors(ancestors);
            for (int i = 0; i < ancestors.size() - 1; ++i)
                assertTrue(ancestors.get(i + 1).hasChild(ancestors.get(i)));
        }
    }

    /**
     * Проходимся по всем узлам кроме корня, получаем его предков
     * и проверяем, что из предков выстраивается цепочка (каждый из них
     * является любому другому сыном-отцом, внуком-дедом, правнуком-прадедом,
     * ...) (проверка новатора на основе report2.txt)
     */
    @Test
    void getAncestorsInnovator2() {
        //Проверяем, что каждый предок есть в списке детей следующего предка
        ft2 = FamilyTree.createTreeByReport(report2);
        martian2 = (InnovatorMartian<String>) ft2.getMartianNode();
        for (Martian<String> descendant: martian2.getDescendants()) {
            ArrayList<Martian<String>> ancestors = new ArrayList<>();
            descendant.getAncestors(ancestors);
            for (int i = 0; i < ancestors.size() - 1; ++i)
                assertTrue(ancestors.get(i + 1).hasChild(ancestors.get(i)));
        }
    }

    /**
     * Проходимся по всем узлам кроме корня, получаем его предков
     * и проверяем, что из предков выстраивается цепочка (каждый из них
     * является любому другому сыном-отцом, внуком-дедом, правнуком-прадедом,
     * ...) (проверка новатора на основе report3.txt)
     */
    @Test
    void getAncestorsInnovator3() {
        //Проверяем, что каждый предок есть в списке детей следующего предка
        ft3 = FamilyTree.createTreeByReport(report3);
        martian3 = (InnovatorMartian<Double>) ft3.getMartianNode();
        for (Martian<Double> descendant: martian3.getDescendants()) {
            ArrayList<Martian<Double>> ancestors = new ArrayList<>();
            descendant.getAncestors(ancestors);
            for (int i = 0; i < ancestors.size() - 1; ++i)
                assertTrue(ancestors.get(i + 1).hasChild(ancestors.get(i)));
        }
    }

    /**
     * Проходимся по всем узлам кроме корня, получаем его предков
     * и проверяем, что из предков выстраивается цепочка (каждый из них
     * является любому другому сыном-отцом, внуком-дедом, правнуком-прадедом,
     * ...) (проверка консерватора на основе report1.txt)
     */
    @Test
    void getAncestorsConservative1() {
        //Проверяем, что каждый предок есть в списке детей следующего предка
        ft1 = FamilyTree.createTreeByReport(report1);
        martian1 = (InnovatorMartian<Integer>) ft1.getMartianNode();
        ConservativeMartian<Integer> martian = new ConservativeMartian<>(martian1);
        for (Martian<Integer> descendant: martian.getDescendants()) {
            ArrayList<Martian<Integer>> ancestors = new ArrayList<>();
            descendant.getAncestors(ancestors);
            for (int i = 0; i < ancestors.size() - 1; ++i)
                assertTrue(ancestors.get(i + 1).hasChild(ancestors.get(i)));
        }
    }

    /**
     * Проходимся по всем узлам кроме корня, получаем его предков
     * и проверяем, что из предков выстраивается цепочка (каждый из них
     * является любому другому сыном-отцом, внуком-дедом, правнуком-прадедом,
     * ...) (проверка консерватора на основе report2.txt)
     */
    @Test
    void getAncestorsConservative2() {
        //Проверяем, что каждый предок есть в списке детей следующего предка
        ft2 = FamilyTree.createTreeByReport(report2);
        martian2 = (InnovatorMartian<String>) ft2.getMartianNode();
        ConservativeMartian<String> martian = new ConservativeMartian<>(martian2);
        for (Martian<String> descendant: martian.getDescendants()) {
            ArrayList<Martian<String>> ancestors = new ArrayList<>();
            descendant.getAncestors(ancestors);
            for (int i = 0; i < ancestors.size() - 1; ++i)
                assertTrue(ancestors.get(i + 1).hasChild(ancestors.get(i)));
        }
    }

    /**
     * Проходимся по всем узлам кроме корня, получаем его предков
     * и проверяем, что из предков выстраивается цепочка (каждый из них
     * является любому другому сыном-отцом, внуком-дедом, правнуком-прадедом,
     * ...) (проверка консерватора на основе report3.txt)
     */
    @Test
    void getAncestorsConservative3() {
        //Проверяем, что каждый предок есть в списке детей следующего предка
        ft3 = FamilyTree.createTreeByReport(report3);
        martian3 = (InnovatorMartian<Double>) ft3.getMartianNode();
        ConservativeMartian<Double> martian = new ConservativeMartian<>(martian3);
        for (Martian<Double> descendant: martian.getDescendants()) {
            ArrayList<Martian<Double>> ancestors = new ArrayList<>();
            descendant.getAncestors(ancestors);
            for (int i = 0; i < ancestors.size() - 1; ++i)
                assertTrue(ancestors.get(i + 1).hasChild(ancestors.get(i)));
        }
    }
}