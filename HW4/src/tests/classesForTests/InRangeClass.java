package classesForTests;

import annotations.InRange;

public class InRangeClass {
    @InRange(min = 1, max = 6)
    private byte num1 = 2;

    @InRange(min = 1, max = 6)
    private short num2 = 2;

    @InRange(min = 1, max = 6)
    private int num3 = 2;

    @InRange(min = 1, max = 6)
    private long num4 = 2;

    @InRange(min = 1, max = 1)
    private int num5 = 1;

    @InRange(min = 10, max = 5)
    private int num6 = 6;

    @InRange(min = -2, max = 2)
    private int num7 = 7;

    @InRange(min = -10, max = 10)
    private String str;
}
