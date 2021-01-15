import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Game {
    static Random rnd = new Random();
    static Scanner in = new Scanner(System.in);
    protected static DecimalFormat df = new DecimalFormat("###.##");
    static int width = 0;
    static int height = 0;
    static int startMoney = 0;
    static int settingsNum = 16383; //num for setting flags in PlayingField
    static PlayingField pf; //game map and information
    static ArrayList<String> colors = new ArrayList<>(Arrays.asList("green", "cyan", "blue",
                                                                    "purple", "red", "yellow"));
    static boolean colorFlag; //if the flag == true game will use colors
    static boolean botInfoFlag = true;

    /**
     * reads string and checks it's correct
     * @param possibleValues
     * @return
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
     * prints game map and all information about player
     * and player's cell
     * @param player
     */
    public static void setInfoAndPrintPF(Player player) {
        if (!(player instanceof Bot)) {
            pf.setMsg(player.getCellMsg());
            pf.setOffer(player.getCellOffer());
            System.out.println();
            System.out.println(pf);
        }
    }

    /**
     * implements player's movie
     * @param player
     */
    public static void playerMove(Player player) {
        int points = rnd.nextInt(11) + 2; //get dice value
        if (colorFlag)
            System.out.println(player.color + player.name + Color.gray + " has " + points + " points");
        else
            System.out.println(player.name + " has " + points + " points");

        pf.movePlayerTo(points, player); //move the player on {points} cells forward
        setInfoAndPrintPF(player);
        Cell cell = player.activeCell; //saves active cell for check
        player.activateCell();
        while (cell instanceof TaxiCell) {
            if (!(player instanceof Bot)) {
                System.out.println("Enter any string for continue...");
                in.next();
            }
            setInfoAndPrintPF(player);
            cell = player.activeCell;
            player.activateCell();
        }
    }

    /**
     * print player's possition
     * @param player player
     */
    public static void printPlayerPos(Player player) {
        int x, y;
        if (player.numOfCell < width) {
            x = player.numOfCell;
            y = 0;
        } else if (player.numOfCell < width + height - 1) {
            x = width - 1;
            y = player.numOfCell - (width - 1);
        } else if (player.numOfCell < 2 * width + height - 2) {
            x = (width - 1) - (player.numOfCell - (width + height - 2));
            y = height - 1;
        } else {
            x = 0;
            y = height - (player.numOfCell - (2 * width + height - 3));
        }
        System.out.println((colorFlag ? player.color : "") + player.name + (colorFlag ? Color.gray : "") +
                " is in the cell (" + x + ", " + y + ")");
    }

    /**
     * implements a game round
     * @param player1
     * @param player2
     * @param whoFirst True - player1 is first; False - player2 is first
     */
    public static void move(Player player1, Player player2, boolean whoFirst) {
        System.out.println();
        if (whoFirst) {
            playerMove(player1);
            playerMove(player2);
        } else {
            playerMove(player2);
            playerMove(player1);
        }
        printPlayerPos(player1);
        printPlayerPos(player2);
    }

    /**
     * prints player settings and responds to player's request
     */
    public static void playerSettings() {
        String answer;
        do {
            pf.printPlayerSettings();
            ArrayList<String> possibleValues = new ArrayList<String>(Arrays.asList("1", "2", "3", "4"));
            answer = readString(possibleValues);

            boolean flag;
            switch (answer) {
                case "1":
                    flag = settingsNum % 2 == 1;
                    settingsNum += (flag ? -1 : 1);
                    break;
                case "2":
                    flag = (settingsNum / 2) % 2 == 1;
                    settingsNum += (flag ? -1 : 1) * 2;
                    break;
                case "3":
                    flag = (settingsNum / 4) % 2 == 1;
                    settingsNum += (flag ? -1 : 1) * 4;
                    break;
            }
            pf.setSettings(settingsNum);
        } while (!answer.equals("4"));
    }

    /**
     * prints shop settings and responds to player's request
     */
    public static void shopSettings() {
        String answer;
        do {
            pf.printShopSettings();
            ArrayList<String> possibleValues = new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5", "6", "7"));
            answer = readString(possibleValues);

            boolean flag;
            switch (answer) {
                case "1":
                    flag = (settingsNum / 8) % 2 == 1;
                    settingsNum += (flag ? -1 : 1) * 8;
                    break;
                case "2":
                    flag = (settingsNum / 16) % 2 == 1;
                    settingsNum += (flag ? -1 : 1) * 16;
                    break;
                case "3":
                    flag = (settingsNum / 32) % 2 == 1;
                    settingsNum += (flag ? -1 : 1) * 32;
                    break;
                case "4":
                    flag = (settingsNum / 64) % 2 == 1;
                    settingsNum += (flag ? -1 : 1) * 64;
                    break;
                case "5":
                    flag = (settingsNum / 256) % 2 == 1;
                    settingsNum += (flag ? -1 : 1) * 256;
                    break;
                case "6":
                    flag = (settingsNum / 128) % 2 == 1;
                    settingsNum += (flag ? -1 : 1) * 128;
                    break;
            }
            pf.setSettings(settingsNum);
        } while (!answer.equals("7"));
    }

    /**
     * prints bank settings and responds to player's request
     */
    public static void bankSettings() {
        String answer;
        do {
            pf.printBankSettings();
            ArrayList<String> possibleValues = new ArrayList<String>(Arrays.asList("1", "2", "3"));
            answer = readString(possibleValues);

            boolean flag;
            switch (answer) {
                case "1":
                    flag = (settingsNum / 512) % 2 == 1;
                    settingsNum += (flag ? -1 : 1) * 512;
                    break;
                case "2":
                    flag = (settingsNum / 1024) % 2 == 1;
                    settingsNum += (flag ? -1 : 1) * 1024;
                    break;
            }
            pf.setSettings(settingsNum);
        } while (!answer.equals("3"));
    }

    /**
     * prints taxi settings and responds to player's request
     */
    public static void taxiSettings() {
        String answer;
        do {
            pf.printTaxiSettings();
            ArrayList<String> possibleValues = new ArrayList<String>(Arrays.asList("1", "2"));
            answer = readString(possibleValues);

            boolean flag;
            switch (answer) {
                case "1":
                    flag = (settingsNum / 4096) % 2 == 1;
                    settingsNum += (flag ? -1 : 1) * 4096;
                    break;
            }
            pf.setSettings(settingsNum);
        } while (!answer.equals("2"));
    }

    /**
     * prints penalty settings and responds to player's request
     */
    public static void penaltySettings() {
        String answer;
        do {
            pf.printPenaltySetting();
            ArrayList<String> possibleValues = new ArrayList<String>(Arrays.asList("1", "2"));
            answer = readString(possibleValues);

            boolean flag;
            switch (answer) {
                case "1":
                    flag = (settingsNum / 2048) % 2 == 1;
                    settingsNum += (flag ? -1 : 1) * 2048;
                    break;
            }
            pf.setSettings(settingsNum);
        } while (!answer.equals("2"));
    }

    /**
     * prints settings menu and responds to player's request
     */
    public static void settings() {
        String answer;
        do {
            System.out.println("Game Settings:");
            System.out.println("");
            System.out.println("   1. Player");
            System.out.println("   2. Shop");
            System.out.println("   3. Bank");
            System.out.println("   4. Taxi");
            System.out.println("   5. Penalty");
            if (colorFlag)
                System.out.println("   6. Color: " + Color.green + "on" +
                        Color.gray + "(only works in IntelliJ IDEA console)");
            else
                System.out.println("   6. Color: off (works only in IntelliJ IDEA console)");
            if (botInfoFlag)
                System.out.println("   7. Info about bot: " + (colorFlag ? Color.green : "") + "on" +
                        (colorFlag ? Color.gray : ""));
            else
                System.out.println("   7. Info about bot: " + (colorFlag ? Color.red : "") + "off" +
                        (colorFlag ? Color.gray : ""));
            System.out.println("");
            System.out.println("   8. Back");
            System.out.println("");
            ArrayList<String> possibleValues = new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8",
                                                         "player", "shop", "bank", "taxi", "penalty", "back", "color"));
            answer = readString(possibleValues);
            switch (answer) {
                case "1":
                case "player":
                    playerSettings();
                    break;
                case "2":
                case "shop":
                    shopSettings();
                    break;
                case "3":
                case "bank":
                    bankSettings();
                    break;
                case "4":
                case "taxi":
                    taxiSettings();
                    break;
                case "5":
                case "penalty":
                    penaltySettings();
                    break;
                case "6":
                case "color":
                    colorFlag = !colorFlag;
                    pf.setColorFlag(colorFlag);
                    Player.setColorFlag(colorFlag);
                    break;
                case "7":
                    settingsNum += (botInfoFlag ? -1 : 1) * 8192;
                    botInfoFlag = !botInfoFlag;
                    pf.setSettings(settingsNum);
                    break;
                default:
            }
        } while (!answer.equals("8") && !answer.equals("back"));
    }

    /**
     * prints main menu and responds to player's request
     */
    public static void mainMenu() {
        String answer;
        do {
            System.out.println("Just Another Monipolia");
            System.out.println();
            System.out.println("   1. Play");
            System.out.println("   2. Settings");
            System.out.println();
            System.out.println("   3. Exit");
            System.out.println();

            ArrayList<String> possibleValues = new ArrayList<String>(Arrays.asList("1", "2", "3",
                                                                                "play", "settings", "exit"));
            answer = readString(possibleValues);
            if (answer.equals("2") || answer.equals("settings"))
                settings();
            if (answer.equals("3") || answer.equals("exit"))
                System.exit(0);
        } while (!answer.equals("1") && !answer.equals("play"));
    }

    /**
     * select color menu
     * @return color name
     */
    static String selectColor() {
        System.out.println("Choose your color:");
        System.out.println("  1. " + Color.green + "Green" + Color.gray);
        System.out.println("  2. " + Color.blue + "Blue" + Color.gray);
        System.out.println("  3. " + Color.cyan + "Cyan" + Color.gray);
        System.out.println("  4. " + Color.purple + "Purple" + Color.gray);
        System.out.println("  5. " + Color.red + "Red" + Color.gray);
        System.out.println("  6. " + Color.yellow + "Yellow" + Color.gray);

        ArrayList<String> posibleValues = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6"));
        posibleValues.addAll(colors);
        String answer = readString(posibleValues);
        switch (answer) {
            case "1":
            case "green":
                colors.remove("green");
                colors.remove("yellow");
                return "green";
            case "2":
            case "blue":
                colors.remove("blue");
                colors.remove("cyan");
                return "blue";
            case "3":
            case "cyan":
                colors.remove("blue");
                colors.remove("cyan");
                return "cyan";
            case "4":
            case "purple":
                colors.remove("purple");
                return "purple";
            case "5":
            case "red":
                colors.remove("red");
                return "red";
            case "6":
            case "yellow":
                colors.remove("yellow");
                colors.remove("green");
                return "yellow";
        }
        return null;
    }

    /**
     * starts a game
     * @param player1
     * @param player2
     */
    public static void game(Player player1, Player player2) {
        pf.setMsg("creditPer: " + df.format(BankCell.getCreditCoeff() * 100) + "%" +
                "\ndebtPer: " + df.format(BankCell.getDebtCoef() * 100) + "%" +
                "\npenaltyPer: " + df.format(PenaltyCell.getPenaltyCoeff() * 100) + "%");
        System.out.println(pf);
        System.out.println("Enter any string for start...");
        in.next();
        int roundCounter = 0;
        boolean whoFirst = rnd.nextBoolean();
        while (!player1.isBankrupt() && !player2.isBankrupt()) {
            System.out.println();
            System.out.println("Round " + ++roundCounter + ":");
            move(player1, player2, whoFirst);
            System.out.println("Enter any string for continue...");
            in.next();
            System.out.println();
        }
        if (player1.isBankrupt()) {
            if (colorFlag)
                System.out.println(player2.color + player2.name + Color.gray + " win!!!!");
            else
                System.out.println(player2.name + " win!!!!");
        } else {
            if (colorFlag)
                System.out.println(player1.color + player1.name + Color.gray + " win!!!!");
            else
                System.out.println(player1.name + " win!!!!");
        }
        System.out.println();
    }

    /**
     * creates new Player
     * @return new Player
     */
    public static Player createPlayer() {
        System.out.print("Input your name: ");
        String name = in.nextLine();
        System.out.println();
        String color = "";
        if (colorFlag) {
            System.out.println();
            color = selectColor();
        }
        return new Player(name, startMoney, color);
    }

    /**
     * checks received arguments and save them
     * @param args
     */
    static void checkArgs(String[] args) {
        try {
            if (args.length != 3)
                throw new IllegalArgumentException();
            width = Integer.parseInt(args[0]);
            height = Integer.parseInt(args[1]);
            startMoney = Integer.parseInt(args[2]);
            if (width < 6 || width > 30 || height < 6 || height > 30)
                throw new NumberFormatException("size of the map was incorrect");
            if (startMoney < 500 || startMoney > 15000)
                throw new NumberFormatException("start money was incorrect");
        } catch (NumberFormatException exception) {
            System.out.println("Incorrect input: " + exception.getMessage());
            System.exit(1);
        } catch (IllegalArgumentException exception) {
            System.out.println("Incorrect count of args");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
//        checkArgs(args); //А вот это закомменти
        String answer;
        do {
            //Раскомменти вот это и поиграй в игру с цветами, так она
            //гораздо лучше. И да, тут я не обрабатываю исключения, поэтому
            //вводи нормальные значения
//            height = in.nextInt();
//            width = in.nextInt();
//            startMoney = in.nextInt();

            pf = new PlayingField(width, height); 
            
            mainMenu();
            //Create players and add them on map
            Player player1 = createPlayer();
            Bot player2 = new Bot("Bot", startMoney, colors.get(rnd.nextInt(colors.size())));
            pf.addPlayer(player1);
            pf.addPlayer(player2);
            
            game(player1, player2);
            //Exit menu
            System.out.println();
            System.out.println("Do you want to exit");
            System.out.println("1. Yes");
            System.out.println("2. No");
            ArrayList<String> possibleValues = new ArrayList<String>(Arrays.asList("1", "2", "yes", "no"));
            answer = readString(possibleValues);
        } while (answer.equals("no") || answer.equals("2"));
    }
}
