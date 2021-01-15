package hw3;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    @Test
    void getX() {
        for (int i = -2147483647; i < 2147483647; i++) {
            Cart cart = new Cart(i/100.0, 0);
            assertEquals(cart.getX(), i/100.0);
        }
    }

    @Test
    void getY() {
        for (int i = -2147483647; i < 2147483647; i++) {
            Cart cart = new Cart(0, i/100.0);
            assertEquals(cart.getY(), i/100.0);
        }
    }

    @Test
    void changeCoordinates() {
        Cart cart = new Cart(0, 0);
        for (int i = -2147483647; i < 2147483647; i++) {
            cart.changeCoordinates(i/100.0, i/100.0);
            assertEquals(cart.getX(), i/100.0);
            assertEquals(cart.getY(), i/100.0);
            cart.changeCoordinates(-i/100.0, -i/100.0);
            assertEquals(cart.getX(), 0);
            assertEquals(cart.getY(), 0);
        }
    }

    @Test
    void testToString() {
        Cart cart = new Cart(0, 0);
        for (int i = -1000000; i < 1000000; i++) {
            cart.changeCoordinates(i / 100.0, i / 100.0);
            assertEquals(cart.toString(), String.format("x = %.2f, y = %.2f;", i/100.0, i/100.0));
            cart.changeCoordinates(-i/100.0, -i/100.0);
        }
    }
}