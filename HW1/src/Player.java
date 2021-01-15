import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Player {
    private static Scanner in = new Scanner(System.in);
    private static DecimalFormat df = new DecimalFormat("###.##"); //double format
    protected static boolean colorFlag = false; //turns on and turns off color
    public String name;
    public Color color;
    public double money; //player's start money
    public int numOfCell; //index of cell, which player has

    protected double debt;
    protected double shopExpenses; //amount of money which player spent for his shops
    protected Cell activeCell; //cell which player has now
    protected PlayingField pf;

    /**
     * create new Player with name, start amount of money and
     * color
     * @param name player's name
     * @param money start amount of player's money
     * @param color player's color
     */
    public Player(String name, double money, String color) {
        this.name = name;
        this.money = money;
        this.color = new Color(color);
        shopExpenses = 0;
        numOfCell = 0;
    }

    /**
     * sets playing field where player exist
     * @param pf playing field
     */
    public void setPlayingField(PlayingField pf) {
        this.pf = pf;
    }

    /**
     * moves player to cell
     * @param cell
     */
    public void moveOn(Cell cell) {
        if (activeCell != null)
            activeCell.DeletePlayer(this);

        activeCell = cell;
        cell.addPlayer(this);
    }

    /**
     * checks that player has a cell
     * @return
     */
    public boolean onCell() {
        return activeCell != null;
    }

    /**
     * changes player's debt
     * @param difference
     */
    public void changeDebt(double difference) {
        debt += difference;
        if (debt < 0) {
            debt = 0;
        }
    }

    /**
     * adds shop expenses
     * @param money money which were spent for shops
     */
    public void addToShopExpenses(double money) {
        if (money < 0)
            throw new IllegalArgumentException("Money must be a positive number");
        shopExpenses += money;
    }

    /**
     * returns player's debt
     * @return player's debt
     */
    public double getDebt() {
        return debt;
    }

    /**
     * pays a player's debt from his money
     */
    public void payDebt() {
        money -= debt;
        debt = 0;
    }

    /**
     * returns shop expenses
     * @return shop expenses
     */
    public double getShopExpenses() {
        return shopExpenses;
    }

    /**
     * player is bankrupt when he don't have money
     * @return
     */
    public boolean isBankrupt() {
        return money < 0;
    }

    /**
     * reads user's answer and checks possibleValues contains
     * his answer
     * @param possibleValues
     * @return
     */
    static private String readString(ArrayList<String> possibleValues) {
        String answer = in.nextLine().toLowerCase();
        while (!possibleValues.contains(answer)) {
            if (colorFlag)
                System.out.println(Color.red + "There is no such answer." + Color.gray);
            else
                System.out.println("There is no such answer.");
            answer = in.nextLine();
        }
        return answer;
    }

    /**
     * reads number and checks its correct
     * @return verified number
     */
    static private int readIntNumber() {
        String answer = in.nextLine();
        while (!answer.matches("[-+]?\\d+")) {
            System.out.println("Incorrect input");
            answer = in.nextLine();
        }
        return Integer.parseInt(answer);
    }

    /**
     * activate shop for player
     * @param shop shop cell
     */
    protected void activateShop(ShopCell shop) {
        if (shop.owner == null) {
            if (shop.getCost() <= money) { //give a chance to buy the shop
                ArrayList<String> possibleValues = new ArrayList<String>(Arrays.asList("no", "yes", "1", "2"));
                String answer = readString(possibleValues);
                if (answer.equals("Yes") || answer.equals("yes") || answer.equals("1"))
                    shop.buyShop(this);
            }
        } else if (name == shop.owner.name) {
            if (money >= shop.getCost()) { //give a chance to improve your shop
                ArrayList<String> possibleValues = new ArrayList<String>(Arrays.asList("no", "yes", "1", "2"));
                String answer = readString(possibleValues);
                if (answer.equals("Yes") || answer.equals("yes") || answer.equals("1"))
                    shop.upgradeShop();
            }
        } else // if owner is other player
            shop.takeCompensation(this);
    }

    /**
     * activate bank for player
     * @param bank bank cell
     */
    protected void activateBank(BankCell bank) {
        if (debt == 0) {
            int answer;
            do {
                answer = readIntNumber();
                if (answer > 0 && answer <= bank.getMaxCredit(this)) //give a chance to take a loan
                    bank.getCredit(this, answer);
                else if (answer > bank.getMaxCredit(this))
                    System.out.println("You can't take so big loan!");
            } while (answer > bank.getMaxCredit(this));
        } else
            bank.payOffDebt(this);
    }

    /**
     * activate taxi cell
     * @param taxi taxi cell
     */
    protected void activateTaxi(TaxiCell taxi) {
        int taxiPoints = taxi.GetTaxiPoints();
        pf.movePlayerTo(taxiPoints, this); //move player on {taxi points} cells forward
    }

    /**
     * activate penalty cell
     * @param penalty penalty cell
     */
    protected void activatePenalty(PenaltyCell penalty) {
        penalty.Penalty(this);
    }

    /**
     * activate player's cell
     */
    public void activateCell() {
        if (activeCell == null)
            throw new IllegalArgumentException("Player don't have a cell");

        if (activeCell instanceof ShopCell) {
            ShopCell shop = (ShopCell) activeCell;
            activateShop(shop);
        } else if (activeCell instanceof BankCell) {
            BankCell bank = (BankCell) activeCell;
            activateBank(bank);
        } else if (activeCell instanceof TaxiCell) {
            TaxiCell taxi = (TaxiCell) activeCell;
            activateTaxi(taxi);
        } else if (activeCell instanceof PenaltyCell) {
            PenaltyCell penalty = (PenaltyCell) activeCell;
            activatePenalty(penalty);
        }
    }

    /**
     * return cell offer
     * @return cell offer
     */
    public String getCellOffer() {
        if (activeCell == null)
            throw new IllegalArgumentException("Player don't have a cell");
        return activeCell.getOffer(this);
    }

    /**
     * return cell message
     * @return cell message
     */
    public String getCellMsg() {
        if (activeCell == null)
            throw new IllegalArgumentException("Player don't have a cell");
        return activeCell.getMsg(this);
    }

    /**
     * return information about player's cell
     * @return information about player's cell
     */
    public String getCellInfo() {
        if (activeCell == null)
            return "Player don't have a cell";
        return activeCell.getInfo();
    }

    /**
     * return player's information
     * @return player's information
     */
    public String getPlayerInfo() {
        DecimalFormat df = new DecimalFormat("###.##");
        return (colorFlag ? color : "") + name + (colorFlag ? Color.gray : "") + ":\n" +
                "money: " + df.format(money) + "\n" +
                "debt: " + df.format(debt) + "\n" +
                "shopEx: " + df.format(shopExpenses) + "\n";
    }

    /**
     * return cell's type
     * @return cell's type
     */
    public String getCellType() {
        if (activeCell instanceof ShopCell)
            return "shop";
        else if (activeCell instanceof BankCell)
            return "bank";
        else if (activeCell instanceof TaxiCell)
            return "taxi";
        else if (activeCell instanceof PenaltyCell)
            return "penalty";
        else
            return "empty";
    }

    /**
     * set value to color flag
     * @param flag
     */
    public static void setColorFlag(boolean flag) {
        colorFlag = flag;
    }

    @Override
    public String toString() {
        if (colorFlag)
            return color + "#" + Color.gray;
        else
            return "y";
    }
}
