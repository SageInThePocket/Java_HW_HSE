package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FamilyTree{

    private static int level = 0; //уровень узла (для корректного вывода)
    final private String typeStr; //тип генетического ключа у марсиан
    final private String treeType; //категория содержащихся марсиан
    private boolean complete = false; //флаг завершения создания дерева
    private Martian martianNode; //начальный узел дерева
    private ArrayList<FamilyTree> childrenNodes; //дочерние узлы

    /**
     * Создает дерево по данным переданного марсианина
     * @param martian марсианин
     */
    public FamilyTree(Martian martian) {
        martianNode = martian;
        typeStr = martian.getValue().getClass().getSimpleName();
        childrenNodes = new ArrayList<>();
        if (martian instanceof InnovatorMartian)
            treeType = "InnovatorMartian";
        else
            treeType = "ConservativeMartian";
        for (Object child: martian.getChildren()) //создаем дочерние узлы
            childrenNodes.add(((Martian)child).getFamilyTree());
        complete = true;
    }

    /**
     * Создает дерево по отчету и устанавливает parent в
     * качестве родителя martianNode
     * @param str отчет
     * @param parent родитель
     */
    FamilyTree(String str, Martian parent) {
        if (str.trim().equals("")) {
            martianNode = null;
            typeStr = "String";
            treeType = "InnovatorMartian";
            childrenNodes = new ArrayList<>();

        } else {
            //полученние первого узла переданного отчета
            String mainNode = str.substring(0, str.indexOf("\n"));
            checkMainNode(mainNode); //проверяем строковое представление марсианина
            String martianType = mainNode.split("\\(")[0]; //категория марсианина
            String valueType = mainNode.split("\\(")[1].split(":")[0]; //тип ген. кода
            String value = mainNode.split("\\(")[1].split(":")[1]; //значение ген. код
            value = value.substring(0, value.length() - 1); //убираем ")" в конце
            checkMartianData(martianType, valueType, value); //проверяем корректность полученных выше данных
            typeStr = valueType;
            treeType = martianType;

            martianNode = createMartian(value, parent); //создаем марсианина по полученным выше данным

            String strChildren = str.substring(str.indexOf("\n") + 1);
            ArrayList<String> childrenList = splitChildren(strChildren);
            //создаем дочерние узлы
            childrenNodes = new ArrayList<>();
            if (childrenList != null)
                for (String strChild : childrenList) {
                    FamilyTree node = new FamilyTree(strChild, martianNode);
                    childrenNodes.add(node);
                }
            checkChildren((InnovatorMartian) martianNode);

            //созание марсианина нужного типа
            if (treeType.equals("ConservativeMartian") && parent == null) {
                martianNode = new ConservativeMartian((InnovatorMartian) martianNode);
                this.childrenNodes = martianNode.getFamilyTree().childrenNodes;
            }
            complete = true;
        }
    }

    public static FamilyTree createTreeByReport(String report) {
        return new FamilyTree(report, null);
    }

    /**
     * Создает марсианина по строковому представлению его ген. ключа
     * и его родителю
     * @param value строковое придставление ген. ключа
     * @param parent родитель
     * @return созданный марсианин
     */
    private InnovatorMartian createMartian(String value, Martian parent) {
        ArrayList children;
        InnovatorMartian martian = null;
        if (typeStr.equals("String")) {
            children = new ArrayList<Martian<String>>();
            martian = new InnovatorMartian<String>(value, parent, children, this);
        } else if (typeStr.equals("Integer")) {
            children = new ArrayList<Martian<Integer>>();
            martian = new InnovatorMartian<Integer>(Integer.parseInt(value), parent,
                    children, this);
        } else if (typeStr.equals("Double")) {
            children = new ArrayList<Martian<Double>>();
            martian = new InnovatorMartian<Double>(Double.parseDouble(value), parent,
                    children, this);
        }
        return martian;
    }

    /**
     * Проверяет корректность детей марсианина
     * @param martian проверяемый марсианин
     */
    private void checkChildren(InnovatorMartian martian) {
        for (Object child: martian.getChildren()) {
            Martian martianChild = (Martian)child;
            if (!typeStr.equals(martianChild.getFamilyTree().typeStr))
                throw new ReportException("Ошибка: в дереве встречен марсианин с иным типом " +
                        "генетического кода (" + martian + ")");
            if (!treeType.equals(martianChild.getFamilyTree().treeType))
                throw new ReportException("Ошибка: в дереве встречен марсианин иной категории");
        }
    }

    /**
     * Проверяет корректность строкого представления узла (марсианина)
     * @param node строковое представление узла (марсианина)
     */
    private static void checkMainNode(String node) throws ReportException {
        if (node.lastIndexOf(' ') != -1)
            throw new ReportException("Неверный формат отчета: неверные отступы");
        if (!node.contains("(") || !node.contains(")") || !node.contains(":"))
            throw new ReportException("Неверный формат отчета: несоответствие формату " +
                    "строкового представления марсианина (" + node + ")");
    }

    /**
     * Проверяет корректность данных о марсианине
     * @param martianType строковое представление категории марсиананина
     * @param valueType строковое представление типа ген. кода
     * @param value строковое представление значения ген. кода
     */
    private void checkMartianData(String martianType, String valueType, String value) {
        if (!martianType.equals("InnovatorMartian") && !martianType.equals("ConservativeMartian"))
            throw new ReportException("Неверный формат отчета: некорректная категория марсианина");
        if (!valueType.equals("String") && !valueType.equals("Double") && !valueType.equals("Integer"))
            throw new ReportException("Неверный формат отчета: некорректный тип генетического кода");
        if (valueType.equals("String") && value.length() > 256)
            throw new ReportException("Неверный формат отчета: некорректный генетический код");
        else if (!tryParse(valueType, value))
            throw new ReportException("Неверный формат отчета: некорректный генетический код");
    }

    /**
     * Проверяет можно ли запарсить строку value в тип данных type,
     * представленный его строковым представлением
     * @param type строковое представление типа данных
     * @param value строковое представление значения
     * @return
     */
    private static boolean tryParse(String type, String value) {
        try {
            if (type.equals("Integer"))
                Integer.parseInt(value);
            else if (type.equals("Double"))
                Double.parseDouble(value);
        } catch (NullPointerException | NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Разбивает отчет (без первой строки) на несколько блоков с детьми
     * и их потомками
     * @param str отчет (без первой строки)
     * @return лист отчетов детей
     */
    private static ArrayList<String> splitChildren(String str) {
        if (str.equals(""))
            return null;

        ArrayList<String> lines = new ArrayList<String>(Arrays.asList(str.split("\n")));
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            line = line.substring(0, line.lastIndexOf(')') + 1);

            //Сдвигаем отчет влево на один отступ
            if (line.lastIndexOf(' ') >= 3)
                lines.set(i, line.substring(4) + "\n");
            else
                throw new ReportException("Неверный формат отчета: неверные отступы");

            //Ставим разделители у каждой строчки без отступа
            line = lines.get(i);
            if (line.lastIndexOf(' ') == -1) {
                line = "!" + line;
                lines.set(i, line);
            }
        }
        //Делим отчет по разделителю и получаем отчеты детей
        ArrayList<String> children = new ArrayList(Arrays.asList(String.join("", lines).split("!")));
        if (!children.get(0).equals("")) //проверяем корректность отчета
            throw new ReportException("Неверный формат отчета: пропущен родитель" +
                    " марсианина " + children.get(0).trim());
        children.remove(0);
        return children;
    }

    /**
     * @return нужный для вывода отступ
     */
    private static String space() {
        return String.join("", Collections.nCopies(level * 4, " "));
    }

    public Martian getMartianNode() {
        return martianNode;
    }

    /**
     * Обновляет дочерние узлы
     */
    public void update() {
        if (complete) {
            childrenNodes = new ArrayList<>();
            if (martianNode != null)
                for (Object child : martianNode.getChildren())
                    childrenNodes.add(((Martian) child).getFamilyTree());
        }
    }

    @Override
    public String toString() {
        String result = space() + martianNode + "\n";
        level++;
        for (FamilyTree childNode: childrenNodes)
            result += childNode;
        level--;
        return result;
    }
}
