package validators;

import annotations.AnyOf;
import annotations.InRange;
import classesForTests.InRangeClass;
import org.junit.jupiter.api.Test;
import validationErrors.ValidationError;

import java.lang.reflect.Field;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class InRangeValidatorTest {

    InRangeClass inRangeClass = new InRangeClass();
    String errorTest6 = "Path: num6\n" +
            "Failed Value: 6\n" +
            "Message: must be in range between 10 and 5.";
    String errorTest7 = "Path: num7\n" +
            "Failed Value: 7\n" +
            "Message: must be in range between -2 and 2.";

    @Test
    void validate1() throws IllegalAccessException {
        Field field = inRangeClass.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        Object val = field.get(inRangeClass);
        InRange annotation = (InRange) field.getAnnotatedType().getDeclaredAnnotations()[0];
        InRangeValidator validator = new InRangeValidator(annotation, "num1");
        assertTrue(validator.validate(val).isEmpty());
    }

    @Test
    void validate2() throws IllegalAccessException {
        Field field = inRangeClass.getClass().getDeclaredFields()[1];
        field.setAccessible(true);
        Object val = field.get(inRangeClass);
        InRange annotation = (InRange) field.getAnnotatedType().getDeclaredAnnotations()[0];
        InRangeValidator validator = new InRangeValidator(annotation, "num2");
        assertTrue(validator.validate(val).isEmpty());
    }

    @Test
    void validate3() throws IllegalAccessException {
        Field field = inRangeClass.getClass().getDeclaredFields()[2];
        field.setAccessible(true);
        Object val = field.get(inRangeClass);
        InRange annotation = (InRange) field.getAnnotatedType().getDeclaredAnnotations()[0];
        InRangeValidator validator = new InRangeValidator(annotation, "num3");
        assertTrue(validator.validate(val).isEmpty());
    }

    @Test
    void validate4() throws IllegalAccessException {
        Field field = inRangeClass.getClass().getDeclaredFields()[3];
        field.setAccessible(true);
        Object val = field.get(inRangeClass);
        InRange annotation = (InRange) field.getAnnotatedType().getDeclaredAnnotations()[0];
        InRangeValidator validator = new InRangeValidator(annotation, "num4");
        assertTrue(validator.validate(val).isEmpty());
    }

    @Test
    void validate5() throws IllegalAccessException {
        Field field = inRangeClass.getClass().getDeclaredFields()[4];
        field.setAccessible(true);
        Object val = field.get(inRangeClass);
        InRange annotation = (InRange) field.getAnnotatedType().getDeclaredAnnotations()[0];
        InRangeValidator validator = new InRangeValidator(annotation, "num5");
        assertTrue(validator.validate(val).isEmpty());
    }

    @Test
    void validate6() throws IllegalAccessException {
        Field field = inRangeClass.getClass().getDeclaredFields()[5];
        field.setAccessible(true);
        Object val = field.get(inRangeClass);
        InRange annotation = (InRange) field.getAnnotatedType().getDeclaredAnnotations()[0];
        InRangeValidator validator = new InRangeValidator(annotation, "num6");
        System.out.println(validator.validate(val).toArray()[0].toString());
    }

    @Test
    void validate7() throws IllegalAccessException {
        Field field = inRangeClass.getClass().getDeclaredFields()[6];
        field.setAccessible(true);
        Object val = field.get(inRangeClass);
        InRange annotation = (InRange) field.getAnnotatedType().getDeclaredAnnotations()[0];
        InRangeValidator validator = new InRangeValidator(annotation, "num7");
        assertEquals(validator.validate(val).toArray()[0].toString(), errorTest7);
    }

    @Test
    void validate8() throws IllegalAccessException {
        Field field = inRangeClass.getClass().getDeclaredFields()[7];
        field.setAccessible(true);
        Object val = field.get(inRangeClass);
        InRange annotation = (InRange) field.getAnnotatedType().getDeclaredAnnotations()[0];
        InRangeValidator validator = new InRangeValidator(annotation, "str");
        //Игнорируется поле так как неподходящий тип
        assertTrue(validator.validate(val).isEmpty());
    }
}