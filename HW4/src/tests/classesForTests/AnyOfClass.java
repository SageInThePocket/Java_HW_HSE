package classesForTests;

import annotations.AnyOf;
import annotations.Constrained;

@Constrained
public class AnyOfClass {
    @AnyOf({"Lol", "Kek", "Cheburek"})
    String str1 = "Lol";

    @AnyOf({"Lol", "Kek", "Cheburek"})
    String str2 = "";

    @AnyOf({"Lol", "Kek", "Cheburek"})
    String str3 = null;

    @AnyOf({})
    String str4 = "";

    @AnyOf({"", "", ""})
    String str5 = "";
}
