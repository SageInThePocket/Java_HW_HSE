package validators;

import classesForTests.PositiveClass;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class PositiveValidatorTest {

    PositiveClass positiveClass = new PositiveClass();
    String errorTest5 = "Path: num5\n" +
            "Failed Value: -8\n" +
            "Message: number must be positive";
    String errorTest6 = "Path: num6\n" +
            "Failed Value: 0\n" +
            "Message: number must be positive";

    @Test
    void validate1() throws IllegalAccessException {
        Field field = positiveClass.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        Object val = field.get(positiveClass);
        PositiveValidator validator = new PositiveValidator("num1");
        assertTrue(validator.validate(val).isEmpty());
    }

    @Test
    void validate2() throws IllegalAccessException {
        Field field = positiveClass.getClass().getDeclaredFields()[1];
        field.setAccessible(true);
        Object val = field.get(positiveClass);
        PositiveValidator validator = new PositiveValidator("num2");
        assertTrue(validator.validate(val).isEmpty());
    }

    @Test
    void validate3() throws IllegalAccessException {
        Field field = positiveClass.getClass().getDeclaredFields()[2];
        field.setAccessible(true);
        Object val = field.get(positiveClass);
        PositiveValidator validator = new PositiveValidator("num3");
        assertTrue(validator.validate(val).isEmpty());
    }

    @Test
    void validate4() throws IllegalAccessException {
        Field field = positiveClass.getClass().getDeclaredFields()[3];
        field.setAccessible(true);
        Object val = field.get(positiveClass);
        PositiveValidator validator = new PositiveValidator("num4");
        assertTrue(validator.validate(val).isEmpty());
    }

    @Test
    void validate5() throws IllegalAccessException {
        Field field = positiveClass.getClass().getDeclaredFields()[4];
        field.setAccessible(true);
        Object val = field.get(positiveClass);
        PositiveValidator validator = new PositiveValidator("num5");
        assertEquals(validator.validate(val).toArray()[0].toString(), errorTest5);
    }

    @Test
    void validate6() throws IllegalAccessException {
        Field field = positiveClass.getClass().getDeclaredFields()[5];
        field.setAccessible(true);
        Object val = field.get(positiveClass);
        PositiveValidator validator = new PositiveValidator("num6");
        assertEquals(validator.validate(val).toArray()[0].toString(), errorTest6);
    }

    @Test
    void validate7() throws IllegalAccessException {
        Field field = positiveClass.getClass().getDeclaredFields()[6];
        field.setAccessible(true);
        Object val = field.get(positiveClass);
        PositiveValidator validator = new PositiveValidator("str1");
        assertTrue(validator.validate(val).isEmpty());
    }
}