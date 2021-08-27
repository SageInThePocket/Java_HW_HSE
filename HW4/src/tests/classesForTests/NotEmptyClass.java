package classesForTests;

import annotations.NotEmpty;

import java.util.*;

public class NotEmptyClass {
    @NotEmpty
    private String str1 = "";

    @NotEmpty
    private ArrayList<Integer> list1 = new ArrayList<>();

    @NotEmpty
    private HashSet<Integer> set1 = new HashSet<>();

    @NotEmpty
    private HashMap<String, Integer> map1 = new HashMap<>();

    @NotEmpty
    private String str2 = "lol";

    @NotEmpty
    private List<Integer> list2 = List.of(1, 2, 3);

    @NotEmpty
    private Set<Integer> set2 = Set.of(1, 2, 4);

    @NotEmpty
    private Map<String, Integer> map2 = Map.ofEntries(Map.entry("lol", 4), Map.entry("kek", 4));

    @NotEmpty
    private String str3 = null;

}
