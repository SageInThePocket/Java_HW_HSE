package classesForTests;

import annotations.NotNull;

import java.util.List;

public class NotNullClass {
    @NotNull
    private String str1 = null;

    @NotNull
    private List<?> list1 = null;

    @NotNull
    private String str2 = "";

    @NotNull
    private List<?> list2 = List.of(1, 2, 3);
}
