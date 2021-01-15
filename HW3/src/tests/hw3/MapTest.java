package hw3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {
    String result1 = "" +
            "      0\n" +
            "# # # @ # # # \n" +
            "# # # @ # # # \n" +
            "# # # @ # # # \n" +
            "@ @ @ O @ @ @  0\n" +
            "# # # @ # # # \n" +
            "# # # @ # # # \n" +
            "# # # @ # # # ";
    String result2 = "" +
            "      0\n" +
            "# # # @ # # # \n" +
            "# # # @ # # # \n" +
            "# # O # # # # \n" +
            "@ @ @ @ @ @ @  0\n" +
            "# # # @ # # # \n" +
            "# # # @ # # # \n" +
            "# # # @ # # # ";
    String result3 = "" +
            "      0\n" +
            "# # # @ # # # \n" +
            "# # # @ # # # \n" +
            "# # # @ # # # \n" +
            "@ @ @ @ @ @ @  0\n" +
            "# # # @ # # # \n" +
            "# # # @ # # # \n" +
            "# # # # # # O ";
    String result4 = "" +
            "      6\n" +
            "# # # @ # # # \n" +
            "# # # @ # # # \n" +
            "# # # @ # # # \n" +
            "@ @ @ @ @ @ @  -6\n" +
            "# # # @ # # # \n" +
            "# # # @ # # # \n" +
            "# # # # # # O ";
    String result5 = "" +
            "      -54\n" +
            "# # # @ # # # \n" +
            "# # # @ # # # \n" +
            "# # # @ # # # \n" +
            "@ @ @ O @ @ @  60\n" +
            "# # # @ # # # \n" +
            "# # # @ # # # \n" +
            "# # # @ # # # ";


    @Test
    void testToString() {
        Cart cart = new Cart(0, 0);
        Map map = new Map(cart, 7, 7);
        map.colorFlag = false;
        assertEquals(map.toString(), result1);
        cart.changeCoordinates(-1, 1);
        assertEquals(map.toString(), result2);
        cart.changeCoordinates(1, -1);
        cart.changeCoordinates(3, -3);
        assertEquals(map.toString(), result3);
        cart.changeCoordinates(-3, 3);
        cart.changeCoordinates(9, -9);
        assertEquals(map.toString(), result4);
        cart.changeCoordinates(-9, 9);
        cart.changeCoordinates(-54, 60);
        assertEquals(map.toString(), result5);
    }
}