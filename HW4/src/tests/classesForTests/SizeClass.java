package classesForTests;

import annotations.NotEmpty;
import annotations.Size;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SizeClass {
    @Size(min = 2, max =  4)
    private List<Integer> list1 = List.of(1, 2, 3);

    @Size(min = 2, max =  4)
    private Map<String, Integer> map1 = Map.ofEntries(Map.entry("lol", 4), Map.entry("kek", 4));

    @Size(min = 2, max = 4)
    private Set<Integer> set1 = Set.of(1, 2, 4);

    @Size(min = 2, max = 4)
    private String str1 = "lol";

    @Size(min = 4, max =  2)
    private List<Integer> list2 = List.of(1, 2, 3);

    @Size(min = 2, max =  2)
    private List<Integer> list3 = List.of(1, 2, 3);

    @Size(min = 2, max =  2)
    private List<Integer> list4 = null;
}
