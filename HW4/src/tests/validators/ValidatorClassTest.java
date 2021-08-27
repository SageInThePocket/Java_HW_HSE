package validators;

import annotations.Negative;
import classesForTests.classesForCommonTests.BookingForm;
import classesForTests.classesForCommonTests.GuestForm;
import classesForTests.classesForCommonTests.NestingCollection;
import classesForTests.classesForCommonTests.Unrelated;
import org.junit.jupiter.api.Test;
import validationErrors.ValidationError;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorClassTest {

    static ArrayList<String> result1 = new ArrayList<>(Arrays.asList(
            "Path: propertyType\n" +
                    "Failed Value: \"Apartment\"\n" +
                    "Message: value must be one of 'House', 'Hostel'.",
            "Path: amenities[1]\n" +
                    "Failed Value: \"Piano\"\n" +
                    "Message: value must be one of 'TV', 'Kitchen'.",
            "Path: guests[1].age\n" +
                    "Failed Value: -3\n" +
                    "Message: must be in range between 0 and 200.",
            "Path: guests[1].firstName\n" +
                    "Failed Value: \"\"\n" +
                    "Message: string must not be blank",
            "Path: guests[0].firstName\n" +
                    "Failed Value: null\n" +
                    "Message: must not be null"
    ));

    static ArrayList<String> result2 = new ArrayList<>(Arrays.asList(
            "Path: guests[1].age\n" +
                    "Failed Value: 203\n" +
                    "Message: must be in range between 0 and 200.",
            "Path: unrelated\n" +
                    "Failed Value: null\n" +
                    "Message: must not be null",
            "Path: propertyType\n" +
                    "Failed Value: null\n" +
                    "Message: must not be null",
            "Path: guests[1].firstName\n" +
                    "Failed Value: null\n" +
                    "Message: must not be null",
            "Path: amenities\n" +
                    "Failed Value: null\n" +
                    "Message: must not be null",
            "Path: guests[3].lastName\n" +
                    "Failed Value: null\n" +
                    "Message: must not be null"
    ));

    static ArrayList<String> result3 = new ArrayList<>(Arrays.asList(
            "Path: list1[1]\n" +
                    "Failed Value: [1]\n" +
                    "Message: size must be in range between 2 and 3.",
            "Path: list1[3][0]\n" +
                    "Failed Value: -1\n" +
                    "Message: number must be positive",
            "Path: map1[str2]\n" +
                    "Failed Value: 5\n" +
                    "Message: number must be negative",
            "Path: list1[3][2]\n" +
                    "Failed Value: -3\n" +
                    "Message: number must be positive",
            "Path: map1[]\n" +
                    "Failed Value: 0\n" +
                    "Message: number must be negative",
            "Path: list1[2]\n" +
                    "Failed Value: [1, 2, 3, 4, 5, 6]\n" +
                    "Message: size must be in range between 2 and 3.",
            "Path: list1[4]\n" +
                    "Failed Value: []\n" +
                    "Message: at least some value must be specified",
            "Path: list1[4]\n" +
                    "Failed Value: []\n" +
                    "Message: size must be in range between 2 and 3.",
            "Path: set1[]\n" +
                    "Failed Value: \"dfdf\"\n" +
                    "Message: value must be one of 'lol', 'kek', 'hochu10'.",
            "Path: set1[]\n" +
                    "Failed Value: \"\"\n" +
                    "Message: value must be one of 'lol', 'kek', 'hochu10'.",

            //Все возможные комбинации элементов в сете
            "Path: set1\n" +
                    "Failed Value: [hochu10, dfdf, ]\n" +
                    "Message: size must be in range between 1 and 2.",
            "Path: set1\n" +
                    "Failed Value: [hochu10, , dfdf]\n" +
                    "Message: size must be in range between 1 and 2.",
            "Path: set1\n" +
                    "Failed Value: [, hochu10, dfdf]\n" +
                    "Message: size must be in range between 1 and 2.",
            "Path: set1\n" +
                    "Failed Value: [dfdf, hochu10, ]\n" +
                    "Message: size must be in range between 1 and 2.",
            "Path: set1\n" +
                    "Failed Value: [dfdf, , hochu10]\n" +
                    "Message: size must be in range between 1 and 2.",
            "Path: set1\n" +
                    "Failed Value: [, dfdf, hochu10]\n" +
                    "Message: size must be in range between 1 and 2."
    ));

    @Test
    void validate1() {
        List<GuestForm> guests = List.of(
                new GuestForm(/*firstName*/ null, /*lastName*/ "Def", /*age*/ 21),
                new GuestForm(/*firstName*/ "", /*lastName*/ "Ijk", /*age*/ -3)
        );
        Unrelated unrelated = new Unrelated(-1);
        BookingForm bookingForm = new BookingForm(
                guests,
                /*amenities*/ List.of("TV", "Piano"),
                /*propertyType*/ "Apartment",
                unrelated
        );

        Validator validator = new ValidatorClass();
        Set<ValidationError> validationErrors = validator.validate(bookingForm);
        for (ValidationError error: validationErrors)
            assertTrue(result1.contains(error.toString()));
        assertEquals(validationErrors.size(), 5);
    }

    @Test
    void validate2() {
        List<GuestForm> guests = List.of(
                new GuestForm(/*firstName*/ "lol", /*lastName*/ "Ijk", /*age*/ 3),
                new GuestForm(/*firstName*/ null, /*lastName*/ "Ijk", /*age*/ 203),
                new GuestForm(/*firstName*/ "lol", /*lastName*/ "Ijk", /*age*/ 3),
                new GuestForm(/*firstName*/ "lol", /*lastName*/ null, /*age*/ 3)
        );
        Unrelated unrelated = new Unrelated(-1);
        BookingForm bookingForm = new BookingForm(
                guests,
                /*amenities*/ null,
                /*propertyType*/ null,
                null
        );

        Validator validator = new ValidatorClass();
        Set<ValidationError> validationErrors = validator.validate(bookingForm);
        for (ValidationError error: validationErrors)
            assertTrue(result2.contains(error.toString()));
        assertEquals(validationErrors.size(), 6);
    }

    @Test
    void validate3() {
        List<List<Integer>> list = List.of(
                List.of(1, 2, 3),
                List.of(1),
                List.of(1, 2, 3, 4, 5, 6),
                List.of(-1, 2, -3),
                List.of()
        );
        Map<String, @Negative Integer> map = Map.ofEntries(
                Map.entry("str1", -3),
                Map.entry("str2", 5),
                Map.entry("", 0)
        );
        Set<String> set = Set.of(
                "hochu10",
                "dfdf",
                ""
        );

        NestingCollection nestingCollection = new NestingCollection(
                list,
                map,
                set
        );

        Validator validator = new ValidatorClass();
        Set<ValidationError> validationErrors = validator.validate(nestingCollection);
        for (ValidationError error: validationErrors)
            assertTrue(result3.contains(error.toString()));
        assertEquals(validationErrors.size(), 11);
    }
}