package validators;

import annotations.Size;
import classesForTests.NotEmptyClass;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class NotEmptyValidatorTest {

    NotEmptyClass notEmptyClass = new NotEmptyClass();
    String errorTest1 = "Path: str1\n" +
            "Failed Value: \n" +
            "Message: string must not be empty";
    String errorTest2 = "Path: list1\n" +
            "Failed Value: []\n" +
            "Message: at least some value must be specified";
    String errorTest3 = "Path: set1\n" +
            "Failed Value: []\n" +
            "Message: at least some value must be specified";
    String errorTest4 = "Path: map1\n" +
            "Failed Value: {}\n" +
            "Message: at least some value must be specified";

    @Test
    void validate1() throws IllegalAccessException {
        Field field = notEmptyClass.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        Object val = field.get(notEmptyClass);
        NotEmptyValidator validator = new NotEmptyValidator("str1");
        assertEquals(validator.validate(val).toArray()[0].toString(), errorTest1);
    }

    @Test
    void validate2() throws IllegalAccessException {
        Field field = notEmptyClass.getClass().getDeclaredFields()[1];
        field.setAccessible(true);
        Object val = field.get(notEmptyClass);
        NotEmptyValidator validator = new NotEmptyValidator("list1");
        assertEquals(validator.validate(val).toArray()[0].toString(), errorTest2);
    }

    @Test
    void validate3() throws IllegalAccessException {
        Field field = notEmptyClass.getClass().getDeclaredFields()[2];
        field.setAccessible(true);
        Object val = field.get(notEmptyClass);
        NotEmptyValidator validator = new NotEmptyValidator("set1");
        assertEquals(validator.validate(val).toArray()[0].toString(), errorTest3);
    }

    @Test
    void validate4() throws IllegalAccessException {
        Field field = notEmptyClass.getClass().getDeclaredFields()[3];
        field.setAccessible(true);
        Object val = field.get(notEmptyClass);
        NotEmptyValidator validator = new NotEmptyValidator("map1");
        assertEquals(validator.validate(val).toArray()[0].toString(), errorTest4);
    }

    @Test
    void validate5() throws IllegalAccessException {
        Field field = notEmptyClass.getClass().getDeclaredFields()[4];
        field.setAccessible(true);
        Object val = field.get(notEmptyClass);
        NotEmptyValidator validator = new NotEmptyValidator("str2");
        assertTrue(validator.validate(val).isEmpty());
    }

    @Test
    void validate6() throws IllegalAccessException {
        Field field = notEmptyClass.getClass().getDeclaredFields()[5];
        field.setAccessible(true);
        Object val = field.get(notEmptyClass);
        NotEmptyValidator validator = new NotEmptyValidator("list2");
        assertTrue(validator.validate(val).isEmpty());
    }

    @Test
    void validate7() throws IllegalAccessException {
        Field field = notEmptyClass.getClass().getDeclaredFields()[6];
        field.setAccessible(true);
        Object val = field.get(notEmptyClass);
        NotEmptyValidator validator = new NotEmptyValidator("set2");
        assertTrue(validator.validate(val).isEmpty());
    }

    @Test
    void validate8() throws IllegalAccessException {
        Field field = notEmptyClass.getClass().getDeclaredFields()[7];
        field.setAccessible(true);
        Object val = field.get(notEmptyClass);
        NotEmptyValidator validator = new NotEmptyValidator("map2");
        assertTrue(validator.validate(val).isEmpty());
    }

    @Test
    void validate9() throws IllegalAccessException {
        Field field = notEmptyClass.getClass().getDeclaredFields()[6];
        field.setAccessible(true);
        Object val = field.get(notEmptyClass);
        NotEmptyValidator validator = new NotEmptyValidator("str3");
        assertTrue(validator.validate(val).isEmpty());
    }
}