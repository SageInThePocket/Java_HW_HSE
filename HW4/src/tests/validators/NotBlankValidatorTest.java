package validators;

import classesForTests.NotBlankClass;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class NotBlankValidatorTest {

    NotBlankClass notBlankClass = new NotBlankClass();
    String errorTest2 = "Path: str2\n" +
            "Failed Value: \"\"\n" +
            "Message: string must not be blank";
    String errorTest3 = "Path: str3\n" +
            "Failed Value: \"     \"\n" +
            "Message: string must not be blank";

    @Test
    void validate1() throws IllegalAccessException {
        Field field = notBlankClass.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        Object val = field.get(notBlankClass);
        NotBlankValidator validator = new NotBlankValidator("str1");
        assertTrue(validator.validate(val).isEmpty());
    }

    @Test
    void validate2() throws IllegalAccessException {
        Field field = notBlankClass.getClass().getDeclaredFields()[1];
        field.setAccessible(true);
        Object val = field.get(notBlankClass);
        NotBlankValidator validator = new NotBlankValidator("str2");
        assertEquals(validator.validate(val).toArray()[0].toString(), errorTest2);
    }

    @Test
    void validate3() throws IllegalAccessException {
        Field field = notBlankClass.getClass().getDeclaredFields()[2];
        field.setAccessible(true);
        Object val = field.get(notBlankClass);
        NotBlankValidator validator = new NotBlankValidator("str3");
        assertEquals(validator.validate(val).toArray()[0].toString(), errorTest3);
    }

    @Test
    void validate4() throws IllegalAccessException {
        Field field = notBlankClass.getClass().getDeclaredFields()[3];
        field.setAccessible(true);
        Object val = field.get(notBlankClass);
        NotBlankValidator validator = new NotBlankValidator("str1");
        assertTrue(validator.validate(val).isEmpty());
    }
}