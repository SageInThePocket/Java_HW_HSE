import java.text.DecimalFormat;
import java.util.ArrayList;

public class Cell {
    protected ArrayList<Player> players;
    protected static DecimalFormat df = new DecimalFormat("###.##");
    protected static boolean colorFlag = false; //turns on and turns off color

    /**
     * create a new cell
     */
    public Cell() {
        players = new ArrayList<Player>();
    }

    /**
     * add player on the cell
     * @param player player who entered to the cell
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * delete player from cell
     * @param player player who left from cell
     */
    public void DeletePlayer(Player player) {
        players.remove(player);
    }

    public String getOffer(Player player) { return ""; }
    public String getMsg(Player player) { return ""; }
    public String getInfo() { return ""; }

    /**
     * set value to a colorFlag
     * @param flag value for colorFlag
     */
    public static void setColorFlag(boolean flag) {
        colorFlag = flag;
    }

    @Override
    public String toString() {
        if (players.size() == 1)
            return "| " + players.get(0) + " |";
        if (players.size() == 2)
            return "|" + players.get(0) + " " + players.get(1) + "|";
        return "|" + players.get(0) + players.get(1) + players.get(2) + "|";
    }
}
