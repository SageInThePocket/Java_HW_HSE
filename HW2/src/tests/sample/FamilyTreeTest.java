package sample;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FamilyTreeTest {

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

    @BeforeEach
    void setUp() {
        Martian.clearExistingValues(); //очищаем лист существуюших значений
    }

    @Test
    void createTreeByReport1() {
        String report;
        FamilyTree ft;
        //Создаем дерево по отчету и проверяем равенство переданного отчета и полученного отчета
        report = readAllText("reports\\report1.txt");
        ft = FamilyTree.createTreeByReport(report);
        assertEquals(ft.toString(), report);
    }

    @Test
    void createTreeByReport2() {
        String report;
        FamilyTree ft;
        //Создаем дерево по отчету и проверяем равенство переданного отчета и полученного отчета
        report = readAllText("reports\\report2.txt");
        ft = FamilyTree.createTreeByReport(report);
        assertEquals(ft.toString(), report);
    }

    @Test
    void createTreeByReport3() {
        String report;
        FamilyTree ft;
        //Создаем дерево по отчету и проверяем равенство переданного отчета и полученного отчета
        report = readAllText("reports\\report3.txt");
        ft = FamilyTree.createTreeByReport(report);
        assertEquals(ft.toString(), report);
    }

    @Test
    void createTreeByReport4() {
        String report;
        FamilyTree ft;
        //Создаем дерево и поверяем сообщение исключения
        report = readAllText("reports\\report4.txt");
        ReportException e = assertThrows(ReportException.class, () -> FamilyTree.createTreeByReport(report));
        assertEquals(e.getMessage(), "Неверный формат отчета: неверные отступы");
    }

    @Test
    void createTreeByReport5() {
        String report;
        FamilyTree ft;
        //Создаем дерево и поверяем сообщение исключения
        report = readAllText("reports\\report5.txt");
        ReportException e = assertThrows(ReportException.class, () -> FamilyTree.createTreeByReport(report));
        assertEquals(e.getMessage(), "Неверный формат отчета: некорректный генетический код");
    }

    @Test
    void createTreeByReport6() {
        String report;
        FamilyTree ft;
        //Создаем дерево и поверяем сообщение исключения
        report = readAllText("reports\\report6.txt");
        ReportException e = assertThrows(ReportException.class, () -> FamilyTree.createTreeByReport(report));
        assertEquals(e.getMessage(), "Ошибка: в дереве встречен марсианин иной категории");
    }

    @Test
    void createTreeByReport7() {
        String report;
        FamilyTree ft;
        //Создаем дерево и поверяем сообщение исключения
        report = readAllText("reports\\report7.txt");
        ReportException e = assertThrows(ReportException.class, () -> FamilyTree.createTreeByReport(report));
        assertEquals(e.getMessage(), "Неверный формат отчета: некорректный генетический код");
    }

    @Test
    void createTreeByReport8() {
        String report;
        FamilyTree ft;
        //Создаем дерево и поверяем сообщение исключения
        report = readAllText("reports\\report8.txt");
        ReportException e = assertThrows(ReportException.class, () -> FamilyTree.createTreeByReport(report));
        assertEquals(e.getMessage(), "Неверный формат отчета: некорректный генетический код");
    }
}