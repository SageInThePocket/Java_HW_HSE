import java.util.ArrayList;
import java.util.Random;

public class TaxiCell extends Cell {
    private static Random rnd = new Random();
    private int distance = rnd.nextInt(3) + 3;

    /**
     * @return taxi points
     */
    public int GetTaxiPoints() {
        int oldDistance = distance;
        distance = rnd.nextInt(3) + 3;
        return oldDistance;
    }

    /**
     * @param player player who enter to the taxi cell
     * @return message for player who enter to the taxi cell
     */
    @Override
    public String getMsg(Player player) {
        return "You are shifted\nforward by " + distance + " cells";
    }

    /**
     * @return information about taxi cell
     */
    @Override
    public String getInfo() {
        StringBuilder info = new StringBuilder();
        if (colorFlag)
            info.append(Color.white + "Taxi:" + Color.gray + "\n");
        else
            info.append("Taxi:\n");
        info.append("distance: " + distance);
        return info.toString();
    }

    @Override
    public String toString() {
        if (players.size() == 0)
            return  "| T |";
        else
            return super.toString();
    }
}
