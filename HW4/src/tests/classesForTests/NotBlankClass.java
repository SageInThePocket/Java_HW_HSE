package classesForTests;

import annotations.NotBlank;

public class NotBlankClass {
    @NotBlank
    private String str1 = "lol";

    @NotBlank
    private String str2 = "";

    @NotBlank
    private String str3 = "     ";

    @NotBlank
    private int num = 1;
}
