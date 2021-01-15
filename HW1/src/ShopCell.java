import java.text.DecimalFormat;
import java.util.Random;

public class ShopCell extends Cell {
    private static Random rnd = new Random();
    private int level; //level of the shop
    public Player owner; //shop's owner
    public int index; //shop index in game map
    private double cost = rnd.nextInt(451) + 50;
    private double compensation = (rnd.nextDouble() * 0.4 + 0.5) * cost;
    private double income = 0; //amount of money which shop gave
    private final double improvementCoeff = rnd.nextDouble() * 1.9 + 0.1;
    private final double compensationCoeff = rnd.nextDouble() * 0.9 + 0.1;

    /**
     * create new shop cell
     * @param index index in cells array
     */
    public ShopCell(int index) {
        this.index = index;
    }

    /**
     * makes the player the owner of the shop and subtract cost of
     * the shop from money of the player
     * @param player player who bay the shop
     */
    public void buyShop(Player player) {
        if (owner != null)
            throw new IllegalArgumentException("The shop has an owner");
        if (player.money < cost)
            throw new IllegalArgumentException(player.name + "don't have enough money to buy the shop");

        player.money -= cost;
        player.addToShopExpenses(cost);
        owner = player;
        level = 1;
    }

    /**
     * upgrade the shop for the owner's money
     */
    public void upgradeShop() {
        if (owner.money < cost)
            throw new IllegalArgumentException("The owner don't have enough money for upgrade");

        owner.money -= cost;
        owner.addToShopExpenses(cost);
        level++;
        cost += improvementCoeff * cost;
        compensation += compensationCoeff * compensation;
    }

    /**
     * take compensation for entering the cell person
     * who aren't owner
     * @param player
     */
    public void takeCompensation(Player player) {
        player.money -= compensation;
        income += compensation;
        owner.money += compensation;
    }

    /**
     * return cost of the shop
     * @return cost of the shop
     */
    public double getCost() {
        return cost;
    }

    /**
     * return compensation of the shop
     * @return compensation of the shop
     */
    public double getCompensation() {
        return compensation;
    }

    /**
     * return shop's offer about buying or upgrading
     * @param player player who enter on the shop
     * @return shop's offer about buying or upgrading
     */
    @Override
    public String getOffer(Player player) {
        if (owner == null)
            if (player.money >= cost) {
                return "Do you want to buy this shop for " + df.format(cost) + "?\n"
                        + "1. Yes\n"
                        + "2. No";
            } else
                return "";
        else if (owner.equals(player) && owner.money >= cost)
            return "Do you want to improve your shop for " + df.format(cost) + "?\n"
                    + "1. Yes\n"
                    + "2. No";
        else
            return "";
    }

    /**
     * return shop's message for player on the shopCell
     * @param player player on the shopCell
     * @return shop's message for player on the shopCell
     */
    @Override
    public String getMsg(Player player) {
        if (owner == null) {
            return "Hmmm very interesting\nshop. You can buy it.\n" +
                    (player.money < cost ? "Oh no, you are poor.." : "");
        } else if (owner.equals(player)) {
            if (income < 200)
                return "This shop is a bit\nuseless. It gave you\n" +
                        (int)income + "$." +
                        (player.money >= cost ? "You can improve\nit..." : "");
            else if (income < 500)
                return "This shop is very\ngood. It gave you\n" +
                        (int)income + "$." +
                        (player.money >= cost ? "You can improve\nit..." : "");
            else
                return "Wow it's god's\nfavourite shop. It\ngave you " +
                        (int)income + "$." +
                        (player.money >= cost ? "You\nshould improve it"
                                : "But\nyou can't improve it");
        } else
            return "Are there socks?\nThere are socks!\nYou bought " +
                    df.format(compensation) + "$\nworth of socks";
    }

    /**
     * return information about cell
     * @return information about cell
     */
    @Override
    public String getInfo() {
        StringBuilder info = new StringBuilder();
        DecimalFormat df = new DecimalFormat("###.##"); // double format
        if (owner == null) {
            info.append((colorFlag ? Color.white : "") + "Shop:" + (colorFlag ? Color.gray : "") + "\n");
            info.append("owner: -\n");
        } else {
            info.append((colorFlag ? owner.color : "") + "Shop:" + (colorFlag ? Color.gray : "") + "\n");
            info.append("owner: " + (colorFlag ? owner.color : "") +
                    owner.name + (colorFlag ? Color.gray : "") + "\n");
        }
        info.append("level: " + level + "\n");
        info.append("improvePer: " + df.format(improvementCoeff * 100) + "%\n");
        info.append("compPer: " + df.format(compensationCoeff * 100) + "%\n");
        info.append("cost: " + df.format(cost) + "\n");
        info.append("compensation: " + df.format(compensation) + "\n");
        return info.toString();
    }

    @Override
    public String toString() {
        if (owner != null && players.size() == 0)
            if (owner instanceof Bot)
                return "|" + (colorFlag ? owner.color : "") + " O " + (colorFlag ? Color.gray : "") + "|";
            else
                return "|" + (colorFlag ? owner.color : "") + " M " + (colorFlag ? Color.gray : "") + "|";
        else if (players.size() == 0)
            return  "| S |";
        else
            return super.toString();
    }
}
