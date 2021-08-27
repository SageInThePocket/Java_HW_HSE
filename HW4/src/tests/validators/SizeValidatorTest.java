package validators;

import annotations.AnyOf;
import annotations.Size;
import classesForTests.SizeClass;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class SizeValidatorTest {

    SizeClass sizeClass = new SizeClass();
    String errorTest5 = "Path: num1\n" +
            "Failed Value: [1, 2, 3]\n" +
            "Message: size must be in range between 4 and 2.";
    String errorTest6 = "Path: num1\n" +
            "Failed Value: [1, 2, 3]\n" +
            "Message: size must be in range between 2 and 2.";

    @Test
    void validate1() throws IllegalAccessException {
        Field field = sizeClass.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        Object val = field.get(sizeClass);
        Size annotation = (Size) field.getAnnotatedType().getDeclaredAnnotations()[0];
        SizeValidator validator = new SizeValidator(annotation, "num1");
        assertTrue(validator.validate(val).isEmpty());
    }

    @Test
    void validate2() throws IllegalAccessException {
        Field field = sizeClass.getClass().getDeclaredFields()[1];
        field.setAccessible(true);
        Object val = field.get(sizeClass);
        Size annotation = (Size) field.getAnnotatedType().getDeclaredAnnotations()[0];
        SizeValidator validator = new SizeValidator(annotation, "num1");
        assertTrue(validator.validate(val).isEmpty());
    }

    @Test
    void validate3() throws IllegalAccessException {
        Field field = sizeClass.getClass().getDeclaredFields()[2];
        field.setAccessible(true);
        Object val = field.get(sizeClass);
        Size annotation = (Size) field.getAnnotatedType().getDeclaredAnnotations()[0];
        SizeValidator validator = new SizeValidator(annotation, "num1");
        assertTrue(validator.validate(val).isEmpty());
    }

    @Test
    void validate4() throws IllegalAccessException {
        Field field = sizeClass.getClass().getDeclaredFields()[3];
        field.setAccessible(true);
        Object val = field.get(sizeClass);
        Size annotation = (Size) field.getAnnotatedType().getDeclaredAnnotations()[0];
        SizeValidator validator = new SizeValidator(annotation, "num1");
        assertTrue(validator.validate(val).isEmpty());
    }

    @Test
    void validate5() throws IllegalAccessException {
        Field field = sizeClass.getClass().getDeclaredFields()[4];
        field.setAccessible(true);
        Object val = field.get(sizeClass);
        Size annotation = (Size) field.getAnnotatedType().getDeclaredAnnotations()[0];
        SizeValidator validator = new SizeValidator(annotation, "num1");
        assertEquals(validator.validate(val).toArray()[0].toString(), errorTest5);
    }

    @Test
    void validate6() throws IllegalAccessException {
        Field field = sizeClass.getClass().getDeclaredFields()[5];
        field.setAccessible(true);
        Object val = field.get(sizeClass);
        Size annotation = (Size) field.getAnnotatedType().getDeclaredAnnotations()[0];
        SizeValidator validator = new SizeValidator(annotation, "num1");
        assertEquals(validator.validate(val).toArray()[0].toString(), errorTest6);
    }

    @Test
    void validate7() throws IllegalAccessException {
        Field field = sizeClass.getClass().getDeclaredFields()[6];
        field.setAccessible(true);
        Object val = field.get(sizeClass);
        Size annotation = (Size) field.getAnnotatedType().getDeclaredAnnotations()[0];
        SizeValidator validator = new SizeValidator(annotation, "num1");
        assertTrue(validator.validate(val).isEmpty());
    }
}