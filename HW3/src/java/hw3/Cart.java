package hw3;

public class Cart {
    private double x; // Координата X телеги
    private double y; // Координата Y телеги

    public Cart(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return координата x телеги
     */
    public double getX() {
        return x;
    }

    /**
     * @return координата y телеги
     */
    public double getY() {
        return y;
    }

    /**
     * Изменяет координаты
     * @param changeX изменение координаты X
     * @param changeY изменение координаты Y
     */
    public synchronized void changeCoordinates(double changeX, double changeY) {
        x += changeX;
        y += changeY;
    }

    @Override
    public String toString() {
        return String.format("x = %.2f, y = %.2f;", x, y);
    }
}
