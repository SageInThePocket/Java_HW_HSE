package validators;

import classesForTests.NotNullClass;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class NotNullValidatorTest {

    NotNullClass notNullClass = new NotNullClass();
    String errorTest1 = "Path: str1\n" +
            "Failed Value: null\n" +
            "Message: must not be null";
    String errorTest2 = "Path: list1\n" +
            "Failed Value: null\n" +
            "Message: must not be null";

    @Test
    void validate1() throws IllegalAccessException {
        Field field = notNullClass.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        Object val = field.get(notNullClass);
        NotNullValidator validator = new NotNullValidator("str1");
        assertEquals(validator.validate(val).toArray()[0].toString(), errorTest1);
    }

    @Test
    void validate2() throws IllegalAccessException {
        Field field = notNullClass.getClass().getDeclaredFields()[1];
        field.setAccessible(true);
        Object val = field.get(notNullClass);
        NotNullValidator validator = new NotNullValidator("list1");
        assertEquals(validator.validate(val).toArray()[0].toString(), errorTest2);
    }

    @Test
    void validate3() throws IllegalAccessException {
        Field field = notNullClass.getClass().getDeclaredFields()[2];
        field.setAccessible(true);
        Object val = field.get(notNullClass);
        NotNullValidator validator = new NotNullValidator("str2");
        assertTrue(validator.validate(val).isEmpty());
    }

    @Test
    void validate4() throws IllegalAccessException {
        Field field = notNullClass.getClass().getDeclaredFields()[3];
        field.setAccessible(true);
        Object val = field.get(notNullClass);
        NotNullValidator validator = new NotNullValidator("list2");
        assertTrue(validator.validate(val).isEmpty());
    }
}