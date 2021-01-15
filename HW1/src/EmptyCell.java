public class EmptyCell extends Cell {

    /**
     * return cell's message
     * @param player
     * @return cell's message
     */
    @Override
    public String getMsg(Player player) {
        return "Just relax there";
    }

    /**
     * return cell's information
     * @return cell's information
     */
    @Override
    public String getInfo() {
        if (colorFlag)
            return Color.white + "Empty:" + Color.gray;
        else
            return "Empty:";
    }

    @Override
    public String toString() {
        if (players.size() == 0)
            return  "| E |";
        else
            return super.toString();
    }
}
