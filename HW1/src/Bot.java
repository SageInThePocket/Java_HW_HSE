import java.text.DecimalFormat;
import java.util.Random;

public class Bot extends Player {
    private static Random rnd = new Random();

    /**
     * Create a Bot
     * @param name bot's name
     * @param money start amount of money
     * @param color bot's color
     */
    public Bot(String name, double money, String color) {
        super(name, money, color);
    }

    /**
     * activate shop cell
     * @param shop shop cell
     */
    @Override
    protected void activateShop(ShopCell shop) {
        DecimalFormat df = new DecimalFormat("###.##"); //double format
        if (shop.owner == null) { //checks owner's existence
            if (shop.getCost() <= money && rnd.nextBoolean()) //gives a chance to buy the shop
                shop.buyShop(this);
        } else if (shop.owner.name == name) {
            if (shop.getCost() <= money && rnd.nextBoolean()) //gives a chance to improve the shop
                shop.upgradeShop();
        }
        else { //shop has an owner
            shop.takeCompensation(this);
            if (colorFlag)
                System.out.println(color + name + Color.gray + " walked by the store and left " +
                    df.format(shop.getCompensation()) + " dollars there");
            else
                System.out.println(name + " walked by the store and left " +
                        df.format(shop.getCompensation()) + " dollars there");
        }
    }

    /**
     * activate bank cell
     * @param bank bank cell
     */
    @Override
    protected void activateBank(BankCell bank) {
        //bot cannot use a bank
    }

    /**
     * activate taxi cell
     * @param taxi taxi cell
     */
    @Override
    protected void activateTaxi(TaxiCell taxi) {
        int taxiPoints = taxi.GetTaxiPoints();
        pf.movePlayerTo(taxiPoints, this); //move bot to {taxi points} cells forward
    }

    /**
     * activate penalty cell
     * @param penalty penalty cell
     */
    @Override
    protected void activatePenalty(PenaltyCell penalty) {
        penalty.Penalty(this); //fines the bot
    }

    @Override
    public String toString() {
        if (colorFlag)
            return super.toString();
        else
            return "b";
    }
}
