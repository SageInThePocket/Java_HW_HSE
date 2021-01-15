import java.text.DecimalFormat;
import java.util.Random;

public class PenaltyCell extends Cell {
    private static final Random rnd = new Random();
    private static final double penaltyCoeff = rnd.nextDouble() * 0.09 + 0.01;

    /**
     * penalizes the player
     * @param player
     */
    public void Penalty(Player player) {
         player.money -= penaltyCoeff * player.money;
    }

    /**
     * return player's penalty
     * @param player player who will be penalty
     * @return player's penalty
     */
    public double getPenalty(Player player) {
        return player.money * penaltyCoeff;
    }

    public static double getPenaltyCoeff() {
        return penaltyCoeff;
    }

    /**
     * return message for player
     * @param player
     * @return message for player
     */
    @Override
    public String getMsg(Player player) {
        double penalty = player.money * penaltyCoeff;
        return "You were fined " + df.format(penalty);
    }

    /**
     * return information about cell
     * @return information about cell
     */
    @Override
    public String getInfo() {
        DecimalFormat df = new DecimalFormat("###.##");
        StringBuilder info = new StringBuilder();
        if (colorFlag)
            info.append(Color.white + "Penalty:" + Color.gray + "\n");
        else
            info.append("Penalty:\n");
        info.append("penaltyPer: " + df.format(penaltyCoeff * 100) + "%\n");
        return info.toString();
    }

    @Override
    public String toString() {
        if (players.size() == 0)
            return  "| % |";
        else
            return super.toString();
    }
}
