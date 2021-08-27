package validators;

import annotations.AnyOf;
import classesForTests.AnyOfClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import validationErrors.ValidationError;

import java.lang.reflect.Field;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AnyOfValidatorTest {

    AnyOfClass anyOfClass = new AnyOfClass();
    String errorTest2 = "Path: str2\n" +
            "Failed Value: \"\"\n" +
            "Message: value must be one of 'Lol', 'Kek', 'Cheburek'.";
    String errorTest3 = "Path: str3\n" +
            "Failed Value: null\n" +
            "Message: value must be one of 'Lol', 'Kek', 'Cheburek'.";
    String errorTest4 = "Path: str4\n" +
            "Failed Value: \"\"\n" +
            "Message: value must be one of .";

    @Test
    void validate1() throws IllegalAccessException {
        Field field = anyOfClass.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        Object val = field.get(anyOfClass);
        AnyOf annotation = (AnyOf) field.getAnnotatedType().getDeclaredAnnotations()[0];
        AnyOfValidator validator = new AnyOfValidator(annotation, "str1");
        assertTrue(validator.validate(val).isEmpty());
    }

    @Test
    void validate2() throws IllegalAccessException {
        Field field = anyOfClass.getClass().getDeclaredFields()[1];
        field.setAccessible(true);
        Object val = field.get(anyOfClass);
        AnyOf annotation = (AnyOf) field.getAnnotatedType().getDeclaredAnnotations()[0];
        AnyOfValidator validator = new AnyOfValidator(annotation, "str2");
        Set<ValidationError> validationErrors = validator.validate(val);
        assertEquals(validationErrors.toArray()[0].toString(), errorTest2);
    }

    @Test
    void validate3() throws IllegalAccessException {
        //Обработка случая с null
        Field field = anyOfClass.getClass().getDeclaredFields()[2];
        field.setAccessible(true);
        Object val = field.get(anyOfClass);
        AnyOf annotation = (AnyOf) field.getAnnotatedType().getDeclaredAnnotations()[0];
        AnyOfValidator validator = new AnyOfValidator(annotation, "str3");
        Set<ValidationError> validationErrors = validator.validate(val);
        assertEquals(validationErrors.toArray()[0].toString(), errorTest3);
    }

    @Test
    void validate4() throws IllegalAccessException {
        Field field = anyOfClass.getClass().getDeclaredFields()[3];
        field.setAccessible(true);
        Object val = field.get(anyOfClass);
        AnyOf annotation = (AnyOf) field.getAnnotatedType().getDeclaredAnnotations()[0];
        AnyOfValidator validator = new AnyOfValidator(annotation, "str4");
        Set<ValidationError> validationErrors = validator.validate(val);
        assertEquals(validationErrors.toArray()[0].toString(), errorTest4);
    }

    @Test
    void validate5() throws IllegalAccessException {
        Field field = anyOfClass.getClass().getDeclaredFields()[4];
        field.setAccessible(true);
        Object val = field.get(anyOfClass);
        AnyOf annotation = (AnyOf) field.getAnnotatedType().getDeclaredAnnotations()[0];
        AnyOfValidator validator = new AnyOfValidator(annotation, "str5");
        assertTrue(validator.validate(val).isEmpty());
    }
}