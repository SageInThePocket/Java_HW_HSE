import java.text.DecimalFormat;
import java.util.*;

public class PlayingField {
    public String msg = ""; //message which will be print
    public String offer = ""; //offer which will be print
    private Player player1;
    private Player player2;

    protected static DecimalFormat df = new DecimalFormat("###.##");

    //Player's flags
    private boolean moneyFlag = true;
    private boolean debtFlag = true;
    private boolean shopExFlag = true;
    //Shop's flags
    private boolean ownerFlag = true;
    private boolean levelFlag = true;
    private boolean compensationCoeffFlag = true;
    private boolean costCoeffFlag = true;
    private boolean costFlag = true;
    private boolean compensationFlag = true;
    //Bank's flags
    private boolean creditCoeffFlag = true;
    private boolean debtCoeffFlag = true;
    //Penalty's flags
    private boolean penaltyCoeffFlag = true;
    //Taxi's flags
    private boolean distanceFlag = true;
    //General flags
    private boolean botInfoFlag = true;
    private boolean colorFlag = false; //turn on and turn off colors
    private boolean msgInCenterFlag;

    private final ArrayList<Cell> cells; //all cells on the map
    private final ArrayList<Cell> side1; //top side of the map
    private final ArrayList<Cell> side2; //right side of the map
    private final ArrayList<Cell> side3; //bottom side of the map
    private final ArrayList<Cell> side4; //bottom side of the map
    private final int width; //width of the map
    private final int height; //height of the map
    private final int countOfCells; //count of cells in the map
    private static final Random rnd = new Random();

    /**
     * Generate PlayingField
     * @param width count of cells in width
     * @param height count of cells in height
     */
    PlayingField(int width, int height){
        if (width < 6 || height < 6 || width > 30 || height > 30)
            throw new IllegalArgumentException("Invalid width or height");

        this.width = width;
        this.height = height;
        msgInCenterFlag = width >= 8 && height >= 8;
        countOfCells = 4 + ((width - 2) + (height - 2)) * 2;

        cells = generateCellsList();

        side1 = new ArrayList<Cell>(cells.subList(0, width));
        side2 = new ArrayList<Cell>(cells.subList(width, width + height - 2));
        side3 = new ArrayList<Cell>(cells.subList(width + height - 2, width * 2 + height - 2));
        side4 = new ArrayList<Cell>(cells.subList(width * 2 + height - 2, countOfCells));
        Collections.reverse(side3); //for normal print
        Collections.reverse(side4); //for normal print
    }

    /**
     * add player to the game map
     * @param player player to add to the game map
     */
    public void addPlayer(Player player) {
        if (player == null)
            throw new IllegalArgumentException("Player was null");
        if (player1 == null) {
            player1 = player;
            player.setPlayingField(this);
            player.moveOn(cells.get(0));
        } else if (player2 == null) {
            player2 = player;
            player.setPlayingField(this);
            player.moveOn(cells.get(0));
        } else
            throw new IllegalArgumentException("You cannot add more 2 players");
    }

    /**
     * moves the player {point} positions forward
     * @param point
     * @param player
     */
    public void movePlayerTo(int point, Player player) {
        if (!player.onCell())
            throw new IllegalArgumentException("Player does not participate in the game");

        player.numOfCell += point;
        if (player.numOfCell >= countOfCells)
            player.numOfCell -= countOfCells;

        Cell cell = cells.get(player.numOfCell);
        player.moveOn(cell);
    }

    /**
     * print player's settings
     */
    public void printPlayerSettings() {
        System.out.println("Player settings");
        System.out.println("");
        if (colorFlag) {
            System.out.println("   1. Display money " + (moneyFlag ? Color.green + "√" + Color.gray : ""));
            System.out.println("   2. Display debt " + (debtFlag ? Color.green + "√" + Color.gray : ""));
            System.out.println("   3. Display shopEx " + (shopExFlag ? Color.green + "√" + Color.gray : ""));
        } else {
            System.out.println("   1. Display money " + (moneyFlag ? "√" : ""));
            System.out.println("   2. Display debt " + (debtFlag ? "√" : ""));
            System.out.println("   3. Display shopEx " + (shopExFlag ? "√" : ""));
        }
        System.out.println("");
        System.out.println("   4. Back");
        System.out.println("");
    }

    /**
     * print shop's settings
     */
    public void printShopSettings() {
        System.out.println("Shop settings");
        System.out.println("");
        if (colorFlag) {
            System.out.println("   1. Display owner " + (ownerFlag ? Color.green + "√" + Color.gray : ""));
            System.out.println("   2. Display level " + (levelFlag ? Color.green + "√" + Color.gray : ""));
            System.out.println("   3. Display compensationCoeff " + (compensationCoeffFlag ? Color.green + "√" + Color.gray : ""));
            System.out.println("   4. Display costCoeff " + (costCoeffFlag ? Color.green + "√" + Color.gray : ""));
            System.out.println("   5. Display cost " + (costFlag ? Color.green + "√" + Color.gray : ""));
            System.out.println("   6. Display compensation " + (compensationFlag ? Color.green + "√" + Color.gray : ""));
        } else {
            System.out.println("   1. Display owner " + (ownerFlag ? "√" : ""));
            System.out.println("   2. Display level " + (levelFlag ? "√" : ""));
            System.out.println("   3. Display compensationCoeff " + (compensationCoeffFlag ? "√" : ""));
            System.out.println("   4. Display costCoeff " + (costCoeffFlag ? "√" : ""));
            System.out.println("   5. Display cost " + (costFlag ? "√" : ""));
            System.out.println("   6. Display compensation " + (compensationFlag ? "√" : ""));
        }
        System.out.println("");
        System.out.println("   7. Back");
        System.out.println("");
    }

    /**
     * print bank's settings
     */
    public void printBankSettings() {
        System.out.println("Bank settings");
        System.out.println("");
        if (colorFlag) {
            System.out.println("   1. Display creditCoeff " + (creditCoeffFlag ? Color.green + "√" + Color.gray : ""));
            System.out.println("   2. Display debtCoeff " + (debtCoeffFlag ? Color.green + "√" + Color.gray : ""));
        } else {
            System.out.println("   1. Display creditCoeff " + (creditCoeffFlag ? "√" : ""));
            System.out.println("   2. Display debtCoeff " + (debtCoeffFlag ? "√" : ""));
        }
        System.out.println("");
        System.out.println("   3. Back");
        System.out.println("");
    }

    /**
     * print taxi's settings
     */
    public void printTaxiSettings() {
        System.out.println("Taxi settings");
        System.out.println("");
        if (colorFlag)
            System.out.println("   1. Display distance " + (distanceFlag ? Color.green + "√" + Color.gray : ""));
        else
            System.out.println("   1. Display distance " + (distanceFlag ? "√" : ""));
        System.out.println("");
        System.out.println("   2. Back");
        System.out.println("");
    }

    /**
     * print penalty's settings
     */
    public void printPenaltySetting() {
        System.out.println("Penalty settings");
        System.out.println("");
        if (colorFlag)
            System.out.println("   1. Display penaltyCoeff " + (penaltyCoeffFlag ? Color.green + "√" + Color.gray : ""));
        else
            System.out.println("   1. Display penaltyCoeff " + (penaltyCoeffFlag ? "√" : ""));
        System.out.println("");
        System.out.println("   2. Back");
        System.out.println("");
    }

    /**
     * set values to flags
     * @param settingsNum number which has values of flags
     */
    public void setSettings(int settingsNum) {
        if (settingsNum > 16383)
            throw new IllegalArgumentException("settingsNum cannot be more than 8192");
        moneyFlag = settingsNum % 2 == 1; //first byte
        settingsNum /= 2;
        debtFlag = settingsNum % 2 == 1; //second byte
        settingsNum /= 2;
        shopExFlag = settingsNum % 2 == 1; //third byte
        settingsNum /= 2;
        ownerFlag = settingsNum % 2 == 1; //forth byte
        settingsNum /= 2;
        levelFlag = settingsNum % 2 == 1; //...
        settingsNum /= 2;
        compensationCoeffFlag = settingsNum % 2 == 1;
        settingsNum /= 2;
        costCoeffFlag = settingsNum % 2 == 1;
        settingsNum /= 2;
        compensationFlag = settingsNum % 2 == 1;
        settingsNum /= 2;
        costFlag = settingsNum % 2 == 1;
        settingsNum /= 2;
        creditCoeffFlag = settingsNum % 2 == 1;
        settingsNum /= 2;
        debtCoeffFlag = settingsNum % 2 == 1;
        settingsNum /= 2;
        penaltyCoeffFlag = settingsNum % 2 == 1;
        settingsNum /= 2;
        distanceFlag = settingsNum % 2 == 1;
        settingsNum /= 2;
        botInfoFlag = settingsNum % 2 == 1;
        settingsNum /= 2;

        if (settingsNum != 0)
            throw new IllegalArgumentException("incorrect settingsNum");
    }

    /**
     * set message
     * @param msg message
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * set offer
     * @param offer offer
     */
    public void setOffer(String offer) {
        this.offer = offer;
    }

    /**
     * set value to colorFlag
     * @param colorFlag value of colorFlag
     */
    public void setColorFlag(boolean colorFlag) {
        this.colorFlag = colorFlag;
        Cell.setColorFlag(colorFlag);
    }

    /**
     * Create list with indexes
     * @param countCells count of cells on one side
     * @return list with indexes
     */
    private ArrayList<Integer> generateIndexes(int countCells) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        int startIndexOnSide = 0;

        for (int i = 0; i < 4; i++) {
            int count = rnd.nextInt(countCells + 1); //count of indexes on the side
            int countCellsOnSide = i % 2 == 0 ? width : height;
            for (int j = 0; j < count; j++) { //add indexes for one side
                int value = rnd.nextInt(countCellsOnSide - 1) + 1 + startIndexOnSide;
                if (!result.contains(value))
                    result.add(value);
            }
            startIndexOnSide += countCellsOnSide - 1;
        }

        return result;
    }

    /**
     * generate arrayList of cells
     * @return arrayList of cells
     */
    private ArrayList<Cell> generateCellsList() {
        ArrayList<Cell> result = new ArrayList<>();
        ArrayList<Integer> taxiIndexes = generateIndexes(2); //indexes where will be created taxi cells
        ArrayList<Integer> penaltyIndexes = generateIndexes(2); //indexes where will be created penalty cells
        int startIndex = 0; //start index on the side

        for(int i = 0; i < 4; i++){
            int countOfCells = i % 2 == 0 ? width : height; //count of cells on the side
            int bankIndex = rnd.nextInt(countOfCells - 1) + 1; //bank's index on the side

            result.add(new EmptyCell()); //add empty cell on corner of the map
            for (int j = 1; j < countOfCells - 1; j++){
                if (j == bankIndex)
                    result.add(new BankCell());
                else if (taxiIndexes.contains(startIndex + j))
                    result.add(new TaxiCell());
                else if (penaltyIndexes.contains(startIndex + j))
                    result.add(new PenaltyCell());
                else
                    result.add(new ShopCell(startIndex + j));
            }
            startIndex += countOfCells - 1;
        }
        return result;
    }

    /**
     * filter shop's information based on values of flags
     * @param shopInfo list with information about shop
     * @return needed information about shop
     */
    private ArrayList<String> filterShopInfo(ArrayList<String> shopInfo) {
        ArrayList<String> result = new ArrayList<String>();
        result.add(shopInfo.get(0));
        if (ownerFlag)
            result.add(shopInfo.get(1));
        if (levelFlag)
            result.add(shopInfo.get(2));
        if (costCoeffFlag)
            result.add(shopInfo.get(3));
        if (compensationCoeffFlag)
            result.add(shopInfo.get(4));
        if (costFlag)
            result.add(shopInfo.get(5));
        if (compensationFlag)
            result.add(shopInfo.get(6));

        return result;
    }

    /**
     * filter bank's information based on values of flags
     * @param bankInfo list with information about shop
     * @return needed information about bank
     */
    private ArrayList<String> filterBankInfo(ArrayList<String> bankInfo) {
        ArrayList<String> result = new ArrayList<String>();
        result.add(bankInfo.get(0));
        if (creditCoeffFlag)
            result.add(bankInfo.get(1));
        if (debtCoeffFlag)
            result.add(bankInfo.get(2));

        return result;
    }

    /**
     * filter penalty's information based on values of flags
     * @param penaltyInfo list with information about shop
     * @return needed information about penalty
     */
    private ArrayList<String> filterPenaltyInfo(ArrayList<String> penaltyInfo) {
        ArrayList<String> result = new ArrayList<String>();
        result.add(penaltyInfo.get(0));
        if (penaltyCoeffFlag)
            result.add(penaltyInfo.get(1));

        return result;
    }

    /**
     * filter taxi's information based on values of flags
     * @param taxiInfo list with information about shop
     * @return needed information about taxi
     */
    private ArrayList<String> filterTaxiInfo(ArrayList<String> taxiInfo) {
        ArrayList<String> result = new ArrayList<String>();
        result.add(taxiInfo.get(0));
        if (distanceFlag)
            result.add(taxiInfo.get(1));

        return result;
    }

    /**
     * filter player's information based on values of flags
     * @param playerInfo list with information about shop
     * @return needed information about player
     */
    private ArrayList<String> filterPlayerInfo(ArrayList<String> playerInfo) {
        ArrayList<String> result = new ArrayList<String>();
        result.add(playerInfo.get(0));
        if (moneyFlag)
            result.add(playerInfo.get(1));
        if (debtFlag)
            result.add(playerInfo.get(2));
        if (shopExFlag)
            result.add(playerInfo.get(3));

        return result;
    }

    /**
     * filter cell's information based on values of flags
     * @param cellInfo information about cell
     * @param type type of the cell
     * @return needed information about cell
     */
    private ArrayList<String> filterCellInfo(ArrayList<String> cellInfo, String type) {
        switch (type) {
            case "shop":
                return filterShopInfo(cellInfo);
            case "bank":
                return filterBankInfo(cellInfo);
            case "penalty":
                return filterPenaltyInfo(cellInfo);
            case "taxi":
                return filterTaxiInfo(cellInfo);
            default:
                return cellInfo;
        }
    }

    /**
     * define max length of string in the arrayList
     * @param list
     * @return max length of string in the arrayList
     */
    private static int maxStringLenght(ArrayList<String> list) {
        int maxLen = 0;
        for (String str : list) {
            if (str.length() > maxLen)
                maxLen = str.length();
        }
        return maxLen;
    }

    /**
     * calculate a top margin
     * @param maxCountLines max count of lines in text box
     * @return top margin
     */
    private int getTopMargin(int maxCountLines) {
        if (height % 2 == maxCountLines % 2)
            return (height - maxCountLines - 2)/2;
        else
            return  (height - maxCountLines - 3)/2;
    }

    /**
     *
     * @param msgList arrayList with message's strings
     * @param msgTopMargin message top margin
     * @param index index of the string
     * @return string representation of the map and all info
     */
    private String createMiddleGapsAndMsg(ArrayList<String> msgList, int msgTopMargin, int index) {
        StringBuilder result = new StringBuilder();

        int countSpaces = (width - 2) * 4 - 1;
        int msgLastIndex = msgTopMargin + msgList.size() - 1;
        if (index >= msgTopMargin && index <= msgLastIndex && msgInCenterFlag) {
            String str = msgList.get(index - msgTopMargin);
            int leftMargin;
            int rightMargin;
            if (str.length() % 2 == countSpaces % 2)
                leftMargin = (countSpaces - str.length()) / 2;
            else
                leftMargin = (countSpaces - str.length() + 1)/2;

            rightMargin = countSpaces - leftMargin - str.length();
            result.append(side4.get(index).toString()
                    + repeatChar(' ', leftMargin)
                    + str + repeatChar(' ', rightMargin)
                    + side2.get(index).toString());
        } else {
            result.append(side4.get(index).toString()
                    + repeatChar(' ', countSpaces)
                    + side2.get(index).toString());
        }

        return result.toString();
    }

    /**
     * create textBox information about player
     * @param playerInfoList arrayList with playerInfo's strings
     * @param margin margin
     * @param index index of string
     * @return one of player's textBox's strings
     */
    private String createPlayerTextBox(ArrayList<String> playerInfoList, int margin, int index) {
        StringBuilder result = new StringBuilder();
        int playerLastIndex = margin + playerInfoList.size() - 1;
        int maxStrLenPlayerInfo = maxStringLenght(playerInfoList);
        if (index >= margin && index <= playerLastIndex) {
            int countOfSpecCh = (index - margin) == 0 && colorFlag ? 9 : 0;
            String str = playerInfoList.get(index - margin);
            int countSpacesAfterStr = maxStrLenPlayerInfo - (str.length() - countOfSpecCh);
            result.append("  " + str + repeatChar(' ', countSpacesAfterStr + 2));
        } else
            result.append(repeatChar(' ', maxStrLenPlayerInfo + 4));
        return result.toString();
    }

    /**
     * create textBox information about cell
     * @param cellInfoList arrayList with cellInfo's strings
     * @param margin margin
     * @param index index of string
     * @return one of cell's textBox's strings
     */
    private String createCellTextBox(ArrayList<String> cellInfoList, int margin, int index) {
        StringBuilder result = new StringBuilder();
        int cellLastIndex = margin + cellInfoList.size() - 1;
        int maxStrLenCellInfo = maxStringLenght(cellInfoList);
        if (index >= margin && index <= cellLastIndex) {
            String str = cellInfoList.get(index - margin);
            int countSpacesAfterStr = maxStrLenCellInfo - str.length();
            result.append("  " + str + repeatChar(' ', countSpacesAfterStr) + "\n");
        } else
            result.append(repeatChar(' ', maxStrLenCellInfo + 4) + "\n");
        return result.toString();
    }

    /**
     * wraps lines so that the information fits into the required number of lines
     * @param strings arrayList of strings
     * @param countOfStrings needed count of strings
     */
    private static void transferString(ArrayList<String> strings, int countOfStrings) {
        int maxStrLen = maxStringLenght(strings);
        int size = strings.size();
        for (int i = 1; i <= size - countOfStrings; i++) {
            int countOfSpaces = maxStrLen - strings.get(i).length();
            String str = strings.get(i) + repeatChar(' ', countOfSpaces + 2) + strings.get(countOfStrings + i - 1);
            strings.set(i, str);
        }
    }

    /**
     * create string representation left and right sides,
     * all information about player and his cell and message on center
     * part of the map
     * @param playerInfoList arrayList with playerInfo's strings
     * @param cellInfoList arrayList with cellInfo's strings
     * @param msgList arrayList with message's strings
     * @return
     */
    private String creteStringRepresentationOfMap(ArrayList<String> playerInfoList, ArrayList<String> botInfo, ArrayList<String> cellInfoList,
                                                  ArrayList<String> msgList) {
        StringBuilder result = new StringBuilder();

        int margin = Math.max(getTopMargin(Math.max(playerInfoList.size(), cellInfoList.size())), 0);
        int msgTopMargin = getTopMargin(msgList.size());

        for (int i = 0; i < side2.size(); i++) {
            result.append(createPlayerTextBox(playerInfoList, margin, i));
            if (botInfoFlag)
                result.append(createPlayerTextBox(botInfo, margin, i));
            result.append(createMiddleGapsAndMsg(msgList, msgTopMargin, i));
            result.append(createCellTextBox(cellInfoList, margin, i));
        }
        return result.toString();
    }

    /**
     * create messageBox under map
     * @param maxPlayerInfoLen max player info len
     * @param msgList arrayList with message's strings
     * @return messageBox under map
     */
    private String createMsgUnderMap(int maxPlayerInfoLen, ArrayList<String> msgList) {
        StringBuilder result = new StringBuilder();
        int countChInWidth = width * 4 + 1;
        int center = maxPlayerInfoLen + 4 + countChInWidth / 2;
        for (String msgStr : msgList) {
            int lineMargin;
            if (countChInWidth % 2 == msgStr.length() % 2)
                lineMargin = center - msgStr.length() / 2;
            else
                lineMargin = center - (msgStr.length() - 1) / 2;
            result.append(repeatChar(' ', lineMargin - 1) + msgStr + "\n");
        }
        result.append("\n");
        return result.toString();
    }

    /**
     * create offerBox under map
     * @param maxPlayerInfoLen max player info len
     * @param offerList arrayList with offer's strings
     * @return offerBox
     */
    private String createOfferUnderMap(int maxPlayerInfoLen, ArrayList<String> offerList) {
        StringBuilder result = new StringBuilder();
        int countChInWidth = width * 4 + 1;
        int maxStrLenOffer = maxStringLenght(offerList);
        int center = maxPlayerInfoLen + 4 + countChInWidth / 2;
        int lineMargin;
        if (countChInWidth % 2 == maxStrLenOffer % 2)
            lineMargin = center - maxStrLenOffer / 2;
        else
            lineMargin = center - (maxStrLenOffer - 1) / 2;
        for (String offerStr : offerList) {
            result.append(repeatChar(' ', lineMargin - 1) + offerStr + "\n");
        }
        return result.toString();
    }

    /**
     * show money changes in money string
     * @param moneyString string for money
     * @return new money string
     */
    private String addMoneyChanges(String moneyString) {
        String str = moneyString;
        if (player1.activeCell instanceof ShopCell) {
            ShopCell shopCell = (ShopCell)player1.activeCell;
            if (shopCell.owner != null && !player1.equals(shopCell.owner))
                str += " - " + df.format(shopCell.getCompensation());
        } else if (player1.activeCell instanceof BankCell) {
            if (player1.debt > 0)
                str += " - " + df.format(player1.debt);
        } else if (player1.activeCell instanceof PenaltyCell) {
            PenaltyCell penaltyCell = (PenaltyCell)player1.activeCell;
            str += " - " + df.format(penaltyCell.getPenalty(player1));
        }
        return str;
    }

    /**
     * create top and bottom sides of map
     * @param cells arrayList of cells
     * @param gapBefore gap before map
     * @return string representation of side
     */
    static private String createTopAndBottomSide(ArrayList<Cell> cells, int gapBefore) {
        StringBuilder result = new StringBuilder();
        result.append(repeatChar(' ', gapBefore));
        for (Cell cell : cells) {
            result.append(cell.toString() + "\b");
        }
        result.append("|\n");

        return result.toString();
    }

    /**
     * repeat char {times} times
     * @param ch char
     * @param times count of times
     * @return string which length is times
     */
    static private String repeatChar(char ch, int times) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < times; i++)
            result.append(ch);
        return result.toString();
    }

    /**
     * create string representation of map with all information about player and his cell
     * @return string representation of map with all information about player and his cell
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        ArrayList<String> playerInfoList = new ArrayList<String>(Arrays.asList(player1.getPlayerInfo().split("\n")));
        ArrayList<String> botInfoList = new ArrayList<String>(Arrays.asList(player2.getPlayerInfo().split("\n")));
        ArrayList<String> cellInfoList = new ArrayList<String>(Arrays.asList(player1.getCellInfo().split("\n")));
        ArrayList<String> msgList = new ArrayList<String>(Arrays.asList(msg.split("\n")));
        ArrayList<String> offerList = new ArrayList<String>(Arrays.asList(offer.split("\n")));

        playerInfoList.set(1, addMoneyChanges(playerInfoList.get(1)));

        playerInfoList = filterPlayerInfo(playerInfoList);
        botInfoList = filterPlayerInfo(botInfoList);
        cellInfoList = filterCellInfo(cellInfoList, player1.getCellType());
        if (cellInfoList.size() > height - 2)
            transferString(cellInfoList, height - 2);

        int gapBefore = maxStringLenght(playerInfoList) + 4;
        gapBefore += botInfoFlag ? maxStringLenght(botInfoList) + 4 : 0;
        result.append(createTopAndBottomSide(side1, gapBefore)); //create top side
        //create left and right sides of the map with information about person and his cell
        result.append(creteStringRepresentationOfMap(playerInfoList, botInfoList, cellInfoList, msgList));
        result.append(createTopAndBottomSide(side3, gapBefore)); //create bottom side
        result.append("\n");

        if (!msgInCenterFlag)
            result.append(createMsgUnderMap(gapBefore, msgList));

        if (!offer.equals(""))
            result.append(createOfferUnderMap(gapBefore, offerList));

        offer = "";
        msg = "";

        return result.toString();
    }
}
