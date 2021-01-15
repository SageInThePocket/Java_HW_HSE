public class Color {
    public static final String gray = "\u001B[0m";
    public static final String white = "\u001B[30m";
    public static final String red = "\u001B[31m";
    public static final String green = "\u001B[32m";
    public static final String yellow = "\u001B[33m";
    public static final String blue = "\u001B[34m";
    public static final String purple = "\u001B[35m";
    public static final String cyan = "\u001B[36m";
    private String color;

    public Color(String color){
        if (color == "gray")
            this.color = gray;
        if (color == "white")
            this.color = white;
        if (color == "red")
            this.color = red;
        if (color == "green")
            this.color = green;
        if (color == "yellow")
            this.color = yellow;
        if (color == "blue")
            this.color = blue;
        if (color == "purple")
            this.color = purple;
        if (color == "cyan")
            this.color = cyan;
        if (color == "")
            this.color = "";
    }

    @Override
    public String toString() {
        return color;
    }
}
