package hw3;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static final Random rnd = new Random();
    static final Scanner in = new Scanner(System.in);
    static int widthOfMap = 11; // Ширина выводимого поля (не меньше 7)
    static int heightOfMap = 11; // Высота выводимого поля (не меньше 7)
    static int workTime = 25; // Время работы программы
    static double x = 0; // Начальное положение телеги
    static double y = 0; // Начальное положение телеги
    static boolean mapFlag = true; // Флаг отображения карты
    static boolean colorFlag = false; // Флаг отображения цветов на карте

    /**
     * Проверяет можно ли запарсить строку в целое число
     * @param str строка
     * @return True - если можно запарсить и False в ином случае
     */
    static private boolean tryParseInt(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    /**
     * Считывает строку до тех пор, пока не будет введенно значение из
     * possibleValues
     * @param possibleValues возможные значения
     * @return введенная пользователем строка
     */
    static private String readString(ArrayList<String> possibleValues) {
        String answer = in.nextLine().toLowerCase();
        while (!possibleValues.contains(answer)) {
            System.out.println("There is no such answer.");
            answer = in.nextLine();
        }
        return answer;
    }

    /**
     * Просит пользователя ввести число, пока он этого не сделает
     * @return считанное число
     */
    static private int readNumber() {
        String str;
        while (!tryParseInt(str = in.nextLine())) {
            System.out.printf("Incorrect input: %s is not a number\n", str);
        }
        return Integer.parseInt(str);
    }

    /**
     * Настройки программы
     */
    public static void settings() {
        String answer;
        do {
            System.out.println("Settings:");
            System.out.println("");
            System.out.println("1. Map: " + (mapFlag ? "On" : "Off"));
            System.out.println("2. Color: " + (colorFlag ? "On" : "Off") + " (only for IntelliJ IDEA)");
            System.out.println("3. Height: " + heightOfMap);
            System.out.println("4. Width: " + widthOfMap);
            System.out.println("5. Work time: " + workTime + "s");
            System.out.println();
            System.out.println("6. Back");

            ArrayList<String> possibleValues = new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5", "6",
                    "map", "color", "back", "height", "width", "work", "work time", "time"));
            answer = readString(possibleValues);
            if (answer.equals("1") || answer.equals("map"))
                mapFlag = !mapFlag;
            else if (answer.equals("2") || answer.equals("color"))
                colorFlag = !colorFlag;
            else if (answer.equals("3") || answer.equals("height")) {
                System.out.printf("Enter odd number (from %d to %d):\n", 7, 21);
                int newVal = readNumber();
                while (newVal < 7 || newVal > 21 || newVal % 2 == 0) {
                    System.out.printf("Number must be odd from %d to %d\n", 7, 21);
                    newVal = readNumber();
                }
                heightOfMap = newVal;
            } else if (answer.equals("4") || answer.equals("width")) {
                System.out.printf("Enter odd number (from %d to %d):\n", 7, 21);
                int newVal = readNumber();
                while (newVal < 7 || newVal > 21 || newVal % 2 == 0) {
                    System.out.printf("Number must be odd and from %d to %d\n", 7, 21);
                    newVal = readNumber();
                }
                widthOfMap = newVal;
            } else if (answer.equals("5") || answer.equals("work") || answer.equals("work time") || answer.equals("time")) {
                System.out.printf("Enter new value (from %d to %d):\n", 10, 60);
                int newVal = readNumber();
                while (newVal < 10 || newVal > 60) {
                    System.out.printf("Value must be from %d to %d\n", 10, 60);
                    newVal = readNumber();
                }
                workTime = newVal;
            }
        } while (!answer.equals("6") && !answer.equals("back"));
    }


    /**
     * Главное меню программы
     */
    public static void menu() {
        String answer;
        do {
            System.out.println("Menu:");
            System.out.println("");
            System.out.println("1. Start");
            System.out.println("2. Settings");
            System.out.println("");
            System.out.println("3. Exit");

            ArrayList<String> possibleValues = new ArrayList<String>(Arrays.asList("1", "2", "3",
                    "start", "settings", "exit"));
            answer = readString(possibleValues);
            if (answer.equals("2") || answer.equals("settings"))
                settings();
            if (answer.equals("3") || answer.equals("exit"))
                System.exit(0);
        } while (!answer.equals("1") && !answer.equals("play"));
    }


    /**
     * Меню выхода, показывающееся в конце игры
     * @return True если пользователь решил выйти и
     * False в ином случае
     */
    public static boolean exitMenu() {
        String answer;
        System.out.println("");
        System.out.println("Do you want to exit");
        System.out.println("1. Yes");
        System.out.println("2. No");
        ArrayList<String> possibleValues = new ArrayList<String>(Arrays.asList("1", "2", "yes", "no"));
        answer = readString(possibleValues);
        return answer.equals("no") || answer.equals("2");
    }

    /**
     * Обрабатывает аргументы в формате "x=10.12", "y=11.41" или
     * "y=10.12", "x=11.41". В случае если это невозможно сделать
     * возвращает false
     * @param args аргументы
     * @return True - если аргументы удалось запарсить и False в ином случае
     */
    public static boolean getTwoStringArgs(String[] args) {
        boolean getX = false;
        boolean getY = false;

        if (args[0].charAt(0) == 'x') {
            x = Double.parseDouble(args[0].split("=")[1]);
            getX = true;
        } else if (args[0].charAt(0) == 'y') {
            y = Double.parseDouble(args[0].split("=")[1]);
            getY = true;
        }

        if (args[1].charAt(0) == 'x') {
            if (!getX)
                x = Double.parseDouble(args[1].split("=")[1]);
            else
                return false;
        } else if (args[1].charAt(0) == 'y') {
            if (!getY)
                y = Double.parseDouble(args[1].split("=")[1]);
            else
                return false;
        } else
            return false;

        return true;
    }

    /**
     * Получает один аргумент вида "x=10.12" или "y=10.12"
     * @param arg аргумент
     * @return True - если аргумент удалось запарсить и False - в ином случае
     */
    public static boolean getOneStringArg(String arg) {
        if (arg.charAt(0) == 'x')
            x = Double.parseDouble(arg.split("=")[1]);
        else if (arg.charAt(0) == 'y')
            y = Double.parseDouble(arg.split("=")[1]);
        else
            return false;
        return true;
    }

    /**
     * Проверяет переданные аргументы
     * @param args переданные аргументы
     * @return True - если аргументы корректные и False - в ином случае
     */
    public static boolean getArgs(String[] args) {
        if (args.length > 2)
            return false;
        else if (args.length == 2) {
            try {
                x = Double.parseDouble(args[0]);
                y = Double.parseDouble(args[1]);
            } catch (NumberFormatException ex) {
                return getTwoStringArgs(args);
            }
        } else if (args.length == 1) {
            try {
                x = Double.parseDouble(args[0]);
            } catch (NumberFormatException ex) {
                return getOneStringArg(args[0]);
            }
        }
        return true;
    }

    /**
     * Создает потоки, выводящие информацию о телеги и карте
     * @param cart телега
     * @param map карта
     */
    public static void createPrinterAndRun(Timer timer, Cart cart, Map map) {
        StatePrinter cartPrinter = new StatePrinter(timer, cart);
        cartPrinter.setName("Cart location");
        cartPrinter.start();
        StatePrinter mapPrinter = null;
        Timer timerForMap = new Timer(workTime, 2);
        if (mapFlag) {
            mapPrinter = new StatePrinter(timerForMap, map);
            mapPrinter.setName("Map");
            mapPrinter.start();
        }
        System.out.print("\nPress Enter to start");
        in.nextLine();
        timer.start();
        try {
            Thread.sleep(10);
            if (mapFlag)
                timerForMap.start();
            timer.join();
            if (mapFlag)
                timerForMap.join();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            cartPrinter.interrupt();
            if (mapFlag)
                mapPrinter.interrupt();
        }
        cartPrinter.interrupt();
        if (mapFlag)
            mapPrinter.interrupt();
    }

    public static void main(String[] args) {
        if (!getArgs(args)) {
            System.out.print("You entered incorrect args...");
            System.exit(-1);
        }
        do {
            menu();
            Timer timer = new Timer(workTime, 2);
            Cart cart = new Cart(x, y);
            Map map = new Map(cart, heightOfMap, widthOfMap);
            if (mapFlag) {
                map = new Map(cart, heightOfMap, widthOfMap);
                map.colorFlag = colorFlag;
            }
            System.out.printf("Start cart location:\n%s\n\n", cart.toString());
            if (mapFlag)
                System.out.printf("Map:\n%s\n\n", map.toString());

            Creature.setTimer(timer);
            Creature.setCart(cart);

            ArrayList<String> names = new ArrayList<>(Arrays.asList("Swan", "Crayfish", "Pike"));
            for (int i = 0; i < 3; i++) {
                Creature creature = new Creature(60 + 120 * i, rnd.nextDouble() * 9 + 1, names.get(i));
                System.out.println(creature);
                creature.start();
            }
            createPrinterAndRun(timer, cart, map);
            System.out.printf("---------------------\nFinal Cart location:\n%s\n\nMap:\n%s\n",
                    cart.toString(), map.toString());

        } while (exitMenu());
    }
}
