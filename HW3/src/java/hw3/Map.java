package hw3;

import com.sun.jdi.connect.Connector;

import java.util.ArrayList;

public class Map {
    private final int height; // Высота карты
    private final int width; // Ширина карты
    private Cart cart; // Телега, отображаемая на карте
    private int centerX; // Координата X центра
    private int centerY; // Координата Y центра
    public boolean colorFlag = false; // Флаг наличия цветов в карте

    public Map(Cart cart, int height, int width) {
        if (height < 6 && width < 6)
            throw new IllegalArgumentException("Too small height or width");
        this.cart = cart;
        centerX = (int)Math.round(cart.getX());
        centerY = (int)Math.round(cart.getY());
        this.height = (height / 2) * 2 + 1;
        this.width = (width / 2) * 2 + 1;
    }

    /**
     * Повторяет строку несколько раз
     * @param str повторяемая строка
     * @param times количество раз, которое ее надо повторить
     * @return повторенная times раз строка str
     */
    private static String repeatString(String str, int times) {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < times; i++)
            strings.add(str);
        return String.join("",strings);
    }

    /**
     * Создает строковое представление карты по координатам телеги относительно центра карты
     * @param relativeCenterX координата X телеги относительно центра
     * @param relativeCenterY координата Y телеги относительно центра
     * @return строковое представление карты
     */
    private String createString(int relativeCenterX, int relativeCenterY) {
        ArrayList<String> lines = new ArrayList<>();
        String spaces = repeatString("  ", width / 2);
        spaces += centerX;
        lines.add(spaces);
        for (int i = height / 2; i >= -height / 2; i--) {
            String line;
            if (i == relativeCenterY) {
                String part1 = repeatString("# ", relativeCenterX + width / 2);
                String part2 = repeatString("# ", width - (relativeCenterX + width / 2) - 1);
                if (colorFlag)
                    line = part1 + "\u001B[33m# \u001B[0m" + part2;
                else {
                    line = part1 + "O " + part2;
                }
            } else {
                String part1 = repeatString("# ", width / 2);
                line = part1 + "@ " + part1;
            }
            if (i == 0) {
                line = line.replace('#', '@');
                line += " " + centerY;
            }
            lines.add(line);
        }
        return String.join("\n", lines);
    }

    @Override
    public String toString() {
        //Вычисляем координаты телеги относительно тела
        int relativeCenterX = (int) Math.round(cart.getX() - centerX);
        int relativeCenterY = (int) Math.round(cart.getY() - centerY);
        //Проверяем нужно ли сместить центр и если нужно - смещаем
        while (relativeCenterX > width / 2) {
            centerX += width - 1;
            relativeCenterX = (int) Math.round(cart.getX() - centerX);
        }
        while (relativeCenterX < -width / 2) {
            centerX -= width - 1;
            relativeCenterX = (int) Math.round(cart.getX() - centerX);
        }
        while (relativeCenterY > height / 2) {
            centerY += height - 1;
            relativeCenterY = (int) Math.round(cart.getY() - centerY);
        }
        while (relativeCenterY < -height / 2) {
            centerY -= height - 1;
            relativeCenterY = (int) Math.round(cart.getY() - centerY);
        }
        return createString(relativeCenterX, relativeCenterY);
    }
}