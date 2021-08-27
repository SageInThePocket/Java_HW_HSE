package validators;

import annotations.InRange;
import annotations.Negative;
import classesForTests.NegativeClass;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class NegativeValidatorTest {

    NegativeClass negativeClass = new NegativeClass();
    String errorTest5 = "Path: num5\n" +
            "Failed Value: 8\n" +
            "Message: number must be negative";
    String getErrorTest6 = "Path: num6\n" +
            "Failed Value: 0\n" +
            "Message: number must be negative";

    @Test
    void validate1() throws IllegalAccessException {
        Field field = negativeClass.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        Object val = field.get(negativeClass);
        NegativeValidator validator = new NegativeValidator("num1");
        assertTrue(validator.validate(val).isEmpty());
    }

    @Test
    void validate2() throws IllegalAccessException {
        Field field = negativeClass.getClass().getDeclaredFields()[1];
        field.setAccessible(true);
        Object val = field.get(negativeClass);
        NegativeValidator validator = new NegativeValidator("num2");
        assertTrue(validator.validate(val).isEmpty());
    }

    @Test
    void validate3() throws IllegalAccessException {
        Field field = negativeClass.getClass().getDeclaredFields()[2];
        field.setAccessible(true);
        Object val = field.get(negativeClass);
        NegativeValidator validator = new NegativeValidator("num3");
        assertTrue(validator.validate(val).isEmpty());
    }

    @Test
    void validate4() throws IllegalAccessException {
        Field field = negativeClass.getClass().getDeclaredFields()[3];
        field.setAccessible(true);
        Object val = field.get(negativeClass);
        NegativeValidator validator = new NegativeValidator("num4");
        assertTrue(validator.validate(val).isEmpty());
    }

    @Test
    void validate5() throws IllegalAccessException {
        Field field = negativeClass.getClass().getDeclaredFields()[4];
        field.setAccessible(true);
        Object val = field.get(negativeClass);
        NegativeValidator validator = new NegativeValidator("num5");
        assertEquals(validator.validate(val).toArray()[0].toString(), errorTest5);
    }

    @Test
    void validate6() throws IllegalAccessException {
        Field field = negativeClass.getClass().getDeclaredFields()[5];
        field.setAccessible(true);
        Object val = field.get(negativeClass);
        NegativeValidator validator = new NegativeValidator("num6");
        System.out.println(validator.validate(val).toArray()[0].toString());
    }

    @Test
    void validate7() throws IllegalAccessException {
        Field field = negativeClass.getClass().getDeclaredFields()[6];
        field.setAccessible(true);
        Object val = field.get(negativeClass);
        NegativeValidator validator = new NegativeValidator("str1");
        assertTrue(validator.validate(val).isEmpty());
    }
}