import java.text.DecimalFormat;
import java.util.Random;

class BankCell extends Cell {
    private static final Random rnd = new Random();
    private static final double debtCoef = rnd.nextDouble() * 2 + 1;// [1; 3]
    private static final double creditCoeff = rnd.nextDouble() * 0.198 + 0.002;// [0.002; 0.2]

    /**
     * get a credit to the player
     * @param player player who take a credit
     * @param credit size of the credit
     */
    public void getCredit(Player player, double credit) {
        if (credit > player.getShopExpenses() * creditCoeff)
            throw new IllegalArgumentException("PLayer can't get too much money");
        player.changeDebt(credit * debtCoef); //add player's debt
        player.money += credit;
    }

    /**
     * pay off credit from the player's money
     * @param player who pay off a credit
     */
    public void payOffDebt(Player player) {
        player.payDebt();
    }

    /**
     * @param player player for whom calculate a max value of credit
     * @return max value of credit for the player
     */
    public double getMaxCredit(Player player) {
        return player.getShopExpenses() * creditCoeff;
    }

    /**
     * @return debt coefficient
     */
    public static double getDebtCoef() {
        return debtCoef;
    }

    /**
     * @return credit coefficient
     */
    public static double getCreditCoeff() {
        return creditCoeff;
    }

    /**
     * @param player player who entered to bank
     * @return offer for player who enter to the bank
     */
    @Override
    public String getOffer(Player player) {
        if (player.debt == 0)
            return "You can take a credit no more than " + df.format(getMaxCredit(player)) + "\n"
                + "How much money do you want to get?";
        return "";
    }

    /**
     * @param player player who entered to bank
     * @return message for player who enter to the bank
     */
    @Override
    public String getMsg(Player player) {
        if (player.debt == 0)
            return "Oh, this is the bank!\nI can take a\ncredit for Iphone 12!";
        else
          return "It's time to pay off\nthe credit...\nYou lost " +
                  df.format(player.debt) + "$";
    }

    /**
     * @return information about bank cell
     */
    @Override
    public String getInfo() {
        DecimalFormat df = new DecimalFormat("###.##");
        StringBuilder info = new StringBuilder();
        if (colorFlag)
            info.append(Color.white + "Bank:" + Color.gray + "\n");
        else
            info.append("Bank:\n");
        info.append("creditPer: " + df.format(creditCoeff * 100) + "%\n");
        info.append("debtPer: " + df.format(debtCoef * 100) + "%\n");
        return info.toString();
    }

    @Override
    public String toString() {
        if (players.size() == 0)
            return  "| $ |";
        else
            return super.toString();
    }
}
